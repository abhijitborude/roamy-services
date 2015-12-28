package com.roamy.web.resource;

import com.roamy.dao.api.*;
import com.roamy.domain.*;
import com.roamy.dto.FavoriteTripAction;
import com.roamy.dto.RestResponse;
import com.roamy.dto.UserActionDto;
import com.roamy.service.api.SmsNotificationService;
import com.roamy.util.RestUtils;
import com.roamy.util.RoamyUtils;
import com.roamy.util.RoamyValidationException;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Abhijit on 11/15/2015.
 */
@RestController
@RequestMapping("/users")
public class UserResource extends IdentityResource<User, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    private UserRepository userRepository;

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

    @Override
    protected JpaRepository<User, Long> getJpaRepository() {
        return userRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public RestResponse createOne(@RequestBody User entity) {
        LOGGER.info("Saving: {}", entity);

        RestResponse response = null;

        try {
            validateForCreate(entity);

            User savedEntity = null;

                    // check if user with same phoneNuumber exists
            User user = userRepository.findByPhoneNumber(entity.getPhoneNumber());

            if (user != null) {
                LOGGER.info("User with phoneNumber({}) already exists: {}", entity.getPhoneNumber(), user);

                // user already exists. Simply update the values and reset codes
                user.setEmail(entity.getEmail());

                // set verification code
                user.setVerificationCode(RoamyUtils.generateVerificationCode());
                user.setVerificationCodeExpiry(RoamyUtils.getVerificationCodeExpiryDate());
                user.setIsVerified(false);

                // set referral code
                user.setReferralCode(RoamyUtils.generateReferralCode());

                user.setLastModifiedBy("test");
                user.setLastModifiedOn(new Date());

                user.setStatus(Status.Inactive);

                savedEntity = userRepository.save(user);
                LOGGER.info("Existing entity updated: {}", savedEntity);

                afterEntityCreated(entity);

            } else {
                enrichForCreate(entity);

                savedEntity = userRepository.save(entity);
                LOGGER.info("Entity Saved: {}", savedEntity);

                afterEntityCreated(entity);
            }

            response = new RestResponse(savedEntity, HttpStatus.OK_200);

        } catch (Throwable t) {
            LOGGER.error("error in save: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }

    @Override
    protected void validateForCreate(User entity) {
        if (!StringUtils.hasText(entity.getPhoneNumber())) {
            throw new RoamyValidationException("Phone number not provided");
        }
        if (!StringUtils.hasText(entity.getEmail())) {
            throw new RoamyValidationException("Email not provided");
        }
    }

    @Override
    protected void enrichForGet(User entity) {

    }

    @Override
    protected void enrichForCreate(User entity) {
        entity.setId(null);

        // set verification code
        entity.setVerificationCode(RoamyUtils.generateVerificationCode());
        entity.setVerificationCodeExpiry(RoamyUtils.getVerificationCodeExpiryDate());
        entity.setIsVerified(false);

        // set referral code
        entity.setReferralCode(RoamyUtils.generateReferralCode());

        entity.setCreatedBy("test");
        entity.setCreatedOn(new Date());
        entity.setLastModifiedBy("test");
        entity.setLastModifiedOn(new Date());

        entity.setStatus(Status.Inactive);
    }

    @Override
    protected void afterEntityCreated(User entity) {
        // send sms with verification code
        smsNotificationService.sendVerificationSms(entity.getPhoneNumber(), entity.getVerificationCode());
    }

    @Override
    protected void addLinks(User entity) {

    }

    @RequestMapping(value = "/{id}/uploadImage", method = RequestMethod.POST)
    public RestResponse uploadImage(@PathVariable Long id, @RequestBody MultipartFile file) {

        LOGGER.info("file: {}", file.getName());

        // return response
        return new RestResponse(null, HttpStatus.OK_200, null, null);

    }

    @RequestMapping(value = "/{id}/action", method = RequestMethod.POST)
    public RestResponse actionUser(@PathVariable Long id, @RequestBody UserActionDto userActionDto) {

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
                smsNotificationService.sendVerificationSms(user.getPhoneNumber(), verificationCode);

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
    public RestResponse getWalletBalance(@PathVariable Long id) {

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
    public RestResponse getNotifications(@PathVariable Long id) {
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
    public RestResponse getFavoriteTrips(@PathVariable Long id) {
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

            List<Trip> trips = new ArrayList<Trip>();
            if (!CollectionUtils.isEmpty(favoriteTrips)) {
                for (FavoriteTrip favoriteTrip : favoriteTrips) {
                    trips.add(favoriteTrip.getTrip());
                }
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
    public RestResponse getFavoriteTripCodes(@PathVariable Long id) {
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

            List<String> tripCodes = new ArrayList<String>();
            if (!CollectionUtils.isEmpty(favoriteTrips)) {
                for (FavoriteTrip favoriteTrip : favoriteTrips) {
                    tripCodes.add(favoriteTrip.getTrip().getCode());
                }
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
    public RestResponse manageFavoriteTrip(@PathVariable Long id, @RequestBody FavoriteTripAction action) {

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
                List<FavoriteTrip> favoriteTrips = favoriteTripRepository.findByUserPhoneNumber(user.getPhoneNumber());

                boolean favoriteExsists = false;
                if (!CollectionUtils.isEmpty(favoriteTrips)) {
                    for (FavoriteTrip favoriteTrip : favoriteTrips) {
                        if (favoriteTrip.getTrip().getCode().equalsIgnoreCase(action.getTripCode())) {
                            favoriteExsists = true;
                        }
                    }
                }

                if (!favoriteExsists) {
                    // find trip
                    Trip trip = tripRepository.findByCode(action.getTripCode());
                    if (trip == null) {
                        throw new RoamyValidationException("Invalid tripCode: " + action.getTripCode());
                    }

                    LOGGER.info("Adding favorite {} for {}", trip, user);

                    // save as favorite trip
                    FavoriteTrip favoriteTrip = new FavoriteTrip(user, trip);
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
    public RestResponse getReservations(@PathVariable Long id,
                                        @RequestParam(value = "active", required = false, defaultValue = "true") String active) {
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
                reservations = reservationRepository.findByUserIdAndStatusAndTripInstanceDateGreaterThanEqualOrderByTripInstanceDateAsc(id, Status.Active, new Date());
            } else {
                reservations = reservationRepository.findTop50ByUserIdAndTripInstanceDateLessThanOrderByTripInstanceDateDesc(id, new Date());
            }

            // return response
            response = new RestResponse(reservations, HttpStatus.OK_200, null, null);

        } catch (Throwable t) {
            LOGGER.error("error in getReservations: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }


}
