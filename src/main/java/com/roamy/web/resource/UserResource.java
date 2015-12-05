package com.roamy.web.resource;

import com.roamy.dao.api.UserRepository;
import com.roamy.domain.Status;
import com.roamy.domain.User;
import com.roamy.dto.RestResponse;
import com.roamy.dto.UserActionDto;
import com.roamy.util.RestUtils;
import com.roamy.util.RoamyUtils;
import com.roamy.util.RoamyValidationException;
import org.eclipse.jetty.http.HttpStatus;
import org.joda.time.DateTime;
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

    @Override
    protected JpaRepository<User, Long> getJpaRepository() {
        return userRepository;
    }

    @Override
    protected void validateForCreate(User entity) {
        if (!StringUtils.hasText(entity.getPhoneNumber())) {
            throw new RoamyValidationException("Phone number not provided");
        }
        if (!StringUtils.hasText(entity.getEmail())) {
            throw new RoamyValidationException("Email not provided");
        }

        User byPhoneNumber = userRepository.findByPhoneNumber(entity.getPhoneNumber());
        if (byPhoneNumber != null) {
            throw new RoamyValidationException("User with Phone Number " + entity.getPhoneNumber() + " already exists");
        }

        User byEmail = userRepository.findByEmail(entity.getEmail());
        if (byEmail != null) {
            throw new RoamyValidationException("User with Email " + entity.getEmail() + " already exists");
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

            // reset the verification code and status if action is "reset" or
            // if for some reason verificationCode is null while activating (which shouldn't happen)
            if ("reset".equalsIgnoreCase(userActionDto.getAction()) ||
                    ("activate".equalsIgnoreCase(userActionDto.getAction()) && user.getVerificationCode() == null)) {

                // 1. generate new activation code
                String verificationCode = RoamyUtils.generateVerificationCode();

                // 2. send new activation code

                // 3. update user object with new activation code and status
                user.setVerificationCode(verificationCode);
                user.setVerificationCodeExpiry(RoamyUtils.getVerificationCodeExpiryDate());
                user.setIsVerified(false);
                user.setStatus(Status.Inactive);
                user.setLastModifiedBy("test");
                user.setLastModifiedOn(new Date());

            } else if ("activate".equalsIgnoreCase(userActionDto.getAction())) {

                if (!user.isVerified()) {

                    // 2. check if the activation code matches
                    if (user.getVerificationCode().equals(userActionDto.getActivationCode())) {

                        // 3. update status of the user
                        user.setIsVerified(true);
                        user.setVerificationCode(null);
                        user.setVerificationCodeExpiry(null);
                        user.setStatus(Status.Active);
                        user.setLastModifiedBy("test");
                        user.setLastModifiedOn(new Date());

                    } else {
                        throw new RoamyValidationException("Activation code is wrong");
                    }
                }
            } else {
                throw new RoamyValidationException("Invalid action: " + userActionDto.getAction());
            }

            userRepository.save(user);

            LOGGER.info("action({}) on userId({}) completed", userActionDto.getAction(), id);

            // return response
            response = new RestResponse(null, HttpStatus.OK_200, null, null);
        } catch (Throwable t) {
            LOGGER.error("error in actionUser: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }
}
