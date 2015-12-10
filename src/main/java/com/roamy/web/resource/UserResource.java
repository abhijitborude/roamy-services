package com.roamy.web.resource;

import com.roamy.dao.api.UserRepository;
import com.roamy.domain.Status;
import com.roamy.domain.User;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
    private SmsNotificationService smsNotificationService;

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
}
