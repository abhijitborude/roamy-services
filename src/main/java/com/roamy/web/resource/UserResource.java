package com.roamy.web.resource;

import com.roamy.config.ConfigProperties;
import com.roamy.dao.api.*;
import com.roamy.domain.*;
import com.roamy.dto.FavoriteTripAction;
import com.roamy.dto.RestResponse;
import com.roamy.dto.UserActionDto;
import com.roamy.dto.UserDto;
import com.roamy.integration.imagelib.dto.ImageLibraryIdentifier;
import com.roamy.integration.imagelib.service.api.ImageLibraryService;
import com.roamy.service.discount.api.RomoneyService;
import com.roamy.service.notification.api.SmsNotificationService;
import com.roamy.util.RestUtils;
import com.roamy.util.RoamyException;
import com.roamy.util.RoamyUtils;
import com.roamy.util.RoamyValidationException;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Abhijit on 11/15/2015.
 */
@RestController
@RequestMapping("/users")
@Api("user")
public class UserResource extends IdentityResource<User, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ImageLibraryService imageLibraryService;

    @Autowired
    private AlertNotificationRepository alertNotificationRepository;

    @Autowired
    private SmsNotificationService smsNotificationService;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private FavoriteTripRepository favoriteTripRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RomoneyService romoneyService;

    @Override
    protected JpaRepository<User, Long> getJpaRepository() {
        return userRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ApiOperation(value = "Create user entity", notes = "Creates a user entity with the user information provided." +
            " If a user with same phoneNumber already exists then simply updates the values of the existing entity." +
            " Otherwise a new entity is created. In both cases, user's verification code is reset and an SMS is sent " +
            " to the user's phoneNumber for verification." +
            "Actual result is contained in the data field of the response.")
    public RestResponse createUser(@ApiParam(name = "userDetails", value = "User Details", required = true)
                                      @RequestBody UserDto userDto) {
        LOGGER.info("Saving: {}", userDto);

        RestResponse response = null;

        try {
            validateForCreate(userDto);

            // check if user with same phoneNumber exists
            User user = userRepository.findByPhoneNumber(userDto.getPhoneNumber());

            if (user == null) {
                user = new User();

                UserType type = userDto.getType() == null ? UserType.ROLE_ROAMY : userDto.getType();
                user.setType(type);

                AccountType accountType = userDto.getAccountType() == null ? AccountType.Phone : userDto.getAccountType();
                user.setAccountType(accountType);

                RoamyUtils.addAuditPropertiesForCreateEntity(user, "test");

            } else {
                LOGGER.info("User with phoneNumber({}) already exists: {}", userDto.getPhoneNumber(), user);

                user.setLastModifiedBy("test");
                user.setLastModifiedOn(new Date());
            }

            updateUserProperties(user, userDto);

            user.setDeviceId(userDto.getDeviceId());

            // set verification code
            user.setVerificationCode(RoamyUtils.generateVerificationCode());
            user.setVerificationCodeExpiry(RoamyUtils.getVerificationCodeExpiryDate());
            user.setIsVerified(false);

            // set referral code
            user.setReferralCode(RoamyUtils.generateReferralCode());

            // set token
            String token = RoamyUtils.generateToken();
            user.setToken((new BCryptPasswordEncoder()).encode(token));
            LOGGER.info("Created token ({}) for user: {}", token, user);

            user.setStatus(Status.Inactive);

            user = userRepository.save(user);
            LOGGER.info("Entity Saved: {}", user);

            // set token to be returned to the user app
            user.setUserToken(token);

            // send sms with verification code
            SmsNotification notification = smsNotificationService.sendVerificationSms(user.getPhoneNumber(), user.getVerificationCode());
            if (Status.Success.equals(notification.getStatus())) {
                response = new RestResponse(user, HttpStatus.OK_200);
            } else {
                throw new RoamyException("Error while sending SMS to the user. Please check the phone number.");
            }

        } catch (Throwable t) {
            LOGGER.error("error in createUser: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }

    protected void validateForCreate(UserDto entity) {
        if (!StringUtils.hasText(entity.getPhoneNumber())) {
            throw new RoamyValidationException("Phone number not provided");
        }
        if (!StringUtils.hasText(entity.getEmail())) {
            throw new RoamyValidationException("Email not provided");
        }
        if (StringUtils.hasText(entity.getCity())) {
            List<City> cities = cityRepository.findByName(entity.getCity());
            if (CollectionUtils.isEmpty(cities) || cities.size() > 1) {
                throw new RoamyValidationException("City not correct: " + entity.getCity());
            }
        }
    }

    protected void updateUserProperties(User user, UserDto entity) {
        user.setPhoneNumber(entity.getPhoneNumber());
        user.setEmail(entity.getEmail());
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        user.setBirthDate(entity.getBirthDate());
        user.setAddress(entity.getAddress());
        user.setCity(entity.getCity());
        user.setPinCode(entity.getPinCode());
        user.setCountry(entity.getCountry());
    }

    @Override
    protected void enrichForGet(User entity) {

    }

    @Override
    protected void addLinks(User entity) {

    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ROLE_ROAMY','ROLE_ADMIN')")
    @ApiOperation(value = "Update user entity", notes = "Updates user entity with a given user ID." +
                        "Actual result is contained in the data field of the response.")
    public RestResponse updateUser(@ApiParam(value = "User ID", required = true) @PathVariable Long id,
                                   @ApiParam(value = "User Details", required = true) @RequestBody UserDto dto) {
        LOGGER.info("updating: {}", dto);

        RestResponse response = null;

        try {
            if (!StringUtils.hasText(dto.getPhoneNumber())) {
                throw new RoamyValidationException("Phone number not provided");
            }
            if (!StringUtils.hasText(dto.getEmail())) {
                throw new RoamyValidationException("Email not provided");
            }
//            if (StringUtils.hasText(dto.getCity())) {
//                List<City> cities = cityRepository.findByName(dto.getCity());
//                if (CollectionUtils.isEmpty(cities) || cities.size() > 1) {
//                    throw new RoamyValidationException("City not correct: " + dto.getCity());
//                }
//            }

            // find the user
            User user = userRepository.findOne(id);
            if (user == null) {
                LOGGER.error("No user found with id: {}", id);
                throw new RoamyValidationException("No user found with id: " + id);
            }

            LOGGER.info("Found {}", user);

            // user already exists. Simply update the values and reset codes
            updateUserProperties(user, dto);

            user.setLastModifiedBy("test");
            user.setLastModifiedOn(new Date());

            User savedEntity = userRepository.save(user);
            LOGGER.info("user updated: {}", savedEntity);

            response = new RestResponse(savedEntity, HttpStatus.OK_200);

        } catch (Throwable t) {
            LOGGER.error("error in update: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }

    @RequestMapping(value = "/{id}/profileImage", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ROAMY','ROLE_ADMIN')")
    @ApiOperation(value = "Update user profile image", notes = "Updates profile image of the user with a given user ID." +
            " Returns URL of the image uploaded. Actual result is contained in the data field of the response.")
    public RestResponse uploadImage(@ApiParam(value = "User ID", required = true)
                                        @PathVariable Long id,
                                    @ApiParam(value = "Multipart file data", required = true)
                                        @RequestParam("file") MultipartFile file) {

        LOGGER.info("uploading profile image for user with id: {}", id);

        RestResponse response = null;

        try {

            String url = null;

            if (!file.isEmpty()) {

                // find the user
                User user = userRepository.findOne(id);
                if (user == null) {
                    LOGGER.error("No user found with id: {}", id);
                    throw new RoamyValidationException("No user found with id: " + id);
                }

                // upload the image
                ImageLibraryIdentifier libraryIdentifier = null;
                try {
                    libraryIdentifier = imageLibraryService.uploadImage(file.getBytes());
                } catch (IOException e) {
                    throw new RoamyValidationException("There was a problem while uploading the image");
                }

                if (libraryIdentifier == null || libraryIdentifier.getUrl() == null) {
                    throw new RoamyValidationException("There was a problem while uploading the image");
                }

                // save the uploaded image id url
                user.setProfileImageId(libraryIdentifier.getId());
                user.setProfileImageUrl(libraryIdentifier.getUrl());
                userRepository.save(user);

                url = libraryIdentifier.getUrl();

                LOGGER.info("Profile image uploaded for user with id: {}", id);
            } else {
                throw new RoamyValidationException("No profile image provided");
            }

            response = new RestResponse(url, HttpStatus.OK_200, null, null);

        } catch (Throwable t) {
            LOGGER.error("error in uploadImage: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }

    @RequestMapping(value = "/{id}/action", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ROAMY','ROLE_ADMIN')")
    @ApiOperation(value = "Take action on user entity", notes = "Takes action on the user with a given user ID." +
                " Which action to perform is provided as a POST payload in the form of userAction. Currently, two" +
                " actions are supported- activate user by matching verificationCode provided or reset the user. In" +
                " case of reset new verification code is generated and sent as an SMS to the user's phone number." +
                " Actual result is contained in the data field of the response.")
    public RestResponse actionUser(@ApiParam(value = "User ID", required = true)
                                        @PathVariable Long id,
                                   @ApiParam(name = "userAction", value = "action can be [activate/reset]. " +
                                           "verificationCode is required when the action is activate.", required = true)
                                        @RequestBody UserActionDto userActionDto) {

        LOGGER.info("taking action on userId({}): {}", id, userActionDto);

        RestResponse response = null;

        try {

            User user = userRepository.findOne(id);

            if (user == null) {
                LOGGER.error("No user found with id: {}", id);
                throw new RoamyValidationException("No user found with id: " + id);
            }

            // reset the verification code and status if action is "reset"
            if ("reset".equalsIgnoreCase(userActionDto.getAction())) {

                // 1. generate new verification code
                String verificationCode = RoamyUtils.generateVerificationCode();

                // 2. send new verification code
                SmsNotification notification = smsNotificationService.sendVerificationSms(user.getPhoneNumber(), verificationCode);
                if (!Status.Success.equals(notification.getStatus())) {
                    throw new RoamyException("Error while sending SMS to the user. Please check the phone number.");
                }

                // 3. update user object with new verification code and status
                user.setVerificationCode(verificationCode);
                user.setVerificationCodeExpiry(RoamyUtils.getVerificationCodeExpiryDate());
                user.setIsVerified(false);
                user.setStatus(Status.Inactive);
                user.setLastModifiedBy("test");
                user.setLastModifiedOn(new Date());

            } else if ("activate".equalsIgnoreCase(userActionDto.getAction())) {

                if (!user.isVerified()) {

                    // 2. check if the verification code matches
                    if (user.getVerificationCode().equals(userActionDto.getVerificationCode())) {

                        LOGGER.info("Verification code matches!");

                        // 3. update status of the user
                        user.setIsVerified(true);
                        user.setVerificationCode(null);
                        user.setVerificationCodeExpiry(null);
                        user.setStatus(Status.Active);
                        user.setLastModifiedBy("test");
                        user.setLastModifiedOn(new Date());

                    } else {
                        LOGGER.error("Verification code does not match");
                        throw new RoamyValidationException("Activation code is wrong");
                    }

                    // 3. award sign up bonus
                    LOGGER.info("awarding sign up bonus of {}", configProperties.getSignUpBonus());
                    romoneyService.creditRomoney(user.getId(), configProperties.getSignUpBonus(), "Signup bonus");
                } else {
                    LOGGER.info("User is already verified");
                }
            } else {
                throw new RoamyValidationException("Invalid action: " + userActionDto.getAction());
            }

            userRepository.save(user);

            LOGGER.info("action({}) on userId({}) completed", userActionDto.getAction(), id);

            // return response
            response = new RestResponse(user, HttpStatus.OK_200, null, null);
        } catch (Throwable t) {
            LOGGER.error("error in actionUser: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }

    @RequestMapping(value = "/{id}/walletBalance", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ROAMY','ROLE_ADMIN')")
    @ApiOperation(value = "Get user's wallet balance", notes = "Fetches current wallet balance of the user." +
                        " Actual result is contained in the data field of the response.")
    public RestResponse getWalletBalance(@ApiParam(value = "User ID", required = true) @PathVariable Long id) {

        LOGGER.info("Finding wallet balance for user id: {}", id);

        RestResponse response = null;

        try {
            // find object
            User user = userRepository.findOne(id);
            if (user == null) {
                throw new RoamyValidationException("Invalid user id: " + id);
            }
            LOGGER.info("Finding favorite trips for {}", user);

            // return response
            response = new RestResponse(user.getWalletBalance(), HttpStatus.OK_200, null, null);

        } catch (Throwable t) {
            LOGGER.error("error in getWalletBalance: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }

    @RequestMapping(value = "/{id}/notifications", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ROAMY','ROLE_ADMIN')")
    @ApiOperation(value = "Get user notifications", notes = "Fetches notifications for the user." +
            " Actual result is contained in the data field of the response.")
    public RestResponse getNotifications(@ApiParam(value = "User ID", required = true) @PathVariable Long id) {
        RestResponse response = null;

        try {
            // find object
            User user = userRepository.findOne(id);
            if (user == null) {
                throw new RoamyValidationException("Invalid user id: " + id);
            }
            LOGGER.info("Finding notifications for {}", user);

            List<Alert> notifications = alertNotificationRepository.findByUserIdAndStatusAndExpiryDateGreaterThan(id, Status.Active, new Date());
            LOGGER.info("{} notifications found for {}", notifications == null ? 0 : notifications.size(), user);

            // return response
            response = new RestResponse(notifications, HttpStatus.OK_200, null, null);

        } catch (Throwable t) {
            LOGGER.error("error in getNotifications: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }


    @RequestMapping(value = "/{id}/favoriteTrips", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ROAMY','ROLE_ADMIN')")
    @ApiOperation(value = "Get user's favorite trips", notes = "Fetches trips marked as favorite by the user." +
            " Actual result is contained in the data field of the response.")
    public RestResponse getFavoriteTrips(@ApiParam(value = "User ID", required = true) @PathVariable Long id) {
        RestResponse response = null;

        try {
            // find object
            User user = userRepository.findOne(id);
            if (user == null) {
                throw new RoamyValidationException("Invalid user id: " + id);
            }
            LOGGER.info("Finding favorite trips for {}", user);

            List<FavoriteTrip> favoriteTrips = favoriteTripRepository.findByUserPhoneNumber(user.getPhoneNumber());
            LOGGER.info("{} favorite trips found for {}", favoriteTrips == null ? 0 : favoriteTrips.size(), user);

            List<Trip> trips = new ArrayList<>();
            if (!CollectionUtils.isEmpty(favoriteTrips)) {
                trips.addAll(favoriteTrips.stream().map(FavoriteTrip::getTrip).collect(Collectors.toList()));
            }

            // return response
            response = new RestResponse(trips, HttpStatus.OK_200, null, null);

        } catch (Throwable t) {
            LOGGER.error("error in getFavoriteTrips: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }

    @RequestMapping(value = "/{id}/favoriteTripCodes", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ROAMY','ROLE_ADMIN')")
    @ApiOperation(value = "Get user's favorite trip code", notes = "Fetches codes of trips marked as favorite by the user." +
            " Actual result is contained in the data field of the response.")
    public RestResponse getFavoriteTripCodes(@ApiParam(value = "User ID", required = true) @PathVariable Long id) {
        RestResponse response = null;

        try {
            // find object
            User user = userRepository.findOne(id);
            if (user == null) {
                throw new RoamyValidationException("Invalid user id: " + id);
            }
            LOGGER.info("Finding favorite trip codes for {}", user);

            List<FavoriteTrip> favoriteTrips = favoriteTripRepository.findByUserPhoneNumber(user.getPhoneNumber());
            LOGGER.info("{} favorite trips found for {}", favoriteTrips == null ? 0 : favoriteTrips.size(), user);

            List<String> tripCodes = new ArrayList<>();
            if (!CollectionUtils.isEmpty(favoriteTrips)) {
                tripCodes.addAll(favoriteTrips.stream().map(favoriteTrip -> favoriteTrip.getTrip().getCode()).collect(Collectors.toList()));
            }

            // return response
            response = new RestResponse(tripCodes, HttpStatus.OK_200, null, null);

        } catch (Throwable t) {
            LOGGER.error("error in getFavoriteTripCodes: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }

    @RequestMapping(value = "/{id}/favoriteTrips", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ROAMY','ROLE_ADMIN')")
    @ApiOperation(value = "Manage user's favorite trips", notes = "Add/remove trip as a favorite trip for a user." +
                        " Does not return any data. Http status reflects success/failure")
    public RestResponse manageFavoriteTrip(@ApiParam(value = "User ID", required = true)
                                                @PathVariable Long id,
                                           @ApiParam(value = "action can be [add/remove] and tripCode is required", required = true)
                                                @RequestBody FavoriteTripAction action) {

        LOGGER.info("Performing {} for userId {}", action, id);

        RestResponse response = null;

        try {
            // find user
            User user = userRepository.findOne(id);
            if (user == null) {
                throw new RoamyValidationException("Invalid user id: " + id);
            }

            // validate action
            if (action == null) {
                throw new RoamyValidationException("Invalid action");
            } else if (action.getTripCode() == null) {
                throw new RoamyValidationException("trip code not specified");
            }

            if (FavoriteTripAction.ADD_ACTION.equals(action.getAction())) {
                FavoriteTrip favoriteTrip = favoriteTripRepository.findByUserIdAndTripCode(user.getId(), action.getTripCode());

                if (favoriteTrip == null) {
                    // find trip
                    Trip trip = tripRepository.findByCode(action.getTripCode());
                    if (trip == null) {
                        throw new RoamyValidationException("Invalid tripCode: " + action.getTripCode());
                    }

                    LOGGER.info("Adding favorite {} for {}", trip, user);

                    // save as favorite trip
                    favoriteTrip = new FavoriteTrip(user, trip);
                    favoriteTripRepository.save(favoriteTrip);
                    LOGGER.info("saved {}", favoriteTrip);

                } else {
                    LOGGER.info("favorite trip already exists");
                }

            } else if (FavoriteTripAction.REMOVE_ACTION.equals(action.getAction())) {
                FavoriteTrip favoriteTrip = favoriteTripRepository.findByUserIdAndTripCode(id, action.getTripCode());

                if (favoriteTrip != null) {
                    favoriteTripRepository.delete(favoriteTrip);
                    LOGGER.info("{} deleted", favoriteTrip);
                } else {
                    LOGGER.info("Trip({}) is not in the favorites list for userId({})", action.getTripCode(), id);
                }
            } else {
                throw new RoamyValidationException("Invalid action");
            }

            // return response
            response = new RestResponse(null, HttpStatus.OK_200, null, null);

        } catch (Throwable t) {
            LOGGER.error("error in addFavoriteTrip: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }

    @RequestMapping(value = "/{id}/reservations", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ROAMY','ROLE_ADMIN')")
    @ApiOperation(value = "Get reservations for user", notes = "Fetches reservations made by the user. Active flag " +
            " can be used to control if only active, inactive or both reservations are returned. When inactive " +
            " reservations are included only top 50 are returned. Actual result is contained in the data field of the response.")
    public RestResponse getReservations(@ApiParam(value = "User ID", required = true) @PathVariable Long id,
                                        @ApiParam(value = "Whether to return only active (active=true), only inactive " +
                                                "(active=false) or all (no value)", required = false)
                                            @RequestParam(value = "active", required = false) String active) {
        RestResponse response = null;

        try {
            // find object
            User user = userRepository.findOne(id);
            if (user == null) {
                throw new RoamyValidationException("Invalid user id: " + id);
            }
            LOGGER.info("Finding reservations for {} with active={}", user, active);

            List<Reservation> reservations = null;

            if ("true".equalsIgnoreCase(active)) {
                // find reservations for user which are active and start date is in future
                reservations = reservationRepository.
                        findByUserIdAndStatusAndStartDateGreaterThanEqualOrderByStartDateAsc(id, Status.Active, new Date());

            } if ("false".equalsIgnoreCase(active)) {

                // find reservations for user which are *NOT* active and start date is in future
                Set<Reservation> inactiveReservations = new HashSet<>();
                inactiveReservations.addAll(reservationRepository.
                        findByUserIdAndStatusNotAndStartDateGreaterThanEqualOrderByStartDateAsc(id, Status.Active, new Date()));

                // find top 50 reservations for user which have start date in the past
                inactiveReservations.addAll(reservationRepository.
                        findTop50ByUserIdAndStartDateLessThanOrderByStartDateDesc(id, new Date()));

                reservations = new ArrayList<>();
                reservations.addAll(inactiveReservations);
            } else {
                // find top 50 reservations for user
                reservations = reservationRepository.findTop50ByUserIdOrderByStartDateDesc(id);
            }

            // return response
            response = new RestResponse(reservations, HttpStatus.OK_200, null, null);

        } catch (Throwable t) {
            LOGGER.error("error in getReservations: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }

    @RequestMapping(value = "/{id}/promotions/referral/{referralCode}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ROAMY','ROLE_ADMIN')")
    @ApiOperation(value = "Applies referral promotion to the user's account", notes = "Awards referral bonus to the user " +
            "that applies the referral code as well as the user who own's the referral code")
    public RestResponse applyReferralCode(@ApiParam(value = "User ID", required = true) @PathVariable Long id,
                                   @ApiParam(value = "Referral Code", required = true) @PathVariable String referralCode) {
        LOGGER.info("Applying referralCode({}) to the userId({})", referralCode, id);

        RestResponse response = null;

        try {
            // find the user
            User user = userRepository.findOne(id);
            if (user == null) {
                LOGGER.error("No user found with id: {}", id);
                throw new RoamyValidationException("No user found with id: " + id);
            }
            LOGGER.info("Found {}", user);

            User referralUser = userRepository.findByReferralCode(referralCode);
            if (referralUser == null) {
                LOGGER.error("No user found with referralCode: {}", referralCode);
                throw new RoamyValidationException("Invalid referralCode: " + referralCode);
            }
            LOGGER.info("Found referralUser {}", referralUser);

            // award Romoney to the user who enters referral code first
            romoneyService.creditRomoney(user.getId(), configProperties.getRefferralSignupBonus(),
                    "Referral Signup bonus. User referred- " + referralUser.getPhoneNumber() + " with referralCode- " + referralCode + ".");

            // then award Romoney to the user who's referral code was entered
            romoneyService.creditRomoney(referralUser.getId(), configProperties.getRefferralBonus(),
                    "Referral bonus. Referred by user- " + user.getPhoneNumber() + " with referralCode- " + referralCode + ".");

            LOGGER.info("Referral successfully processed");

            response = new RestResponse(null, HttpStatus.OK_200);

        } catch (Throwable t) {
            LOGGER.error("error in applyReferralCode: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }


}
