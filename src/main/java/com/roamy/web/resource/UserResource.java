package com.roamy.web.resource;

import com.roamy.dao.api.UserRepository;
import com.roamy.domain.Status;
import com.roamy.domain.User;
import com.roamy.dto.RestResponse;
import com.roamy.dto.UserActionDto;
import com.roamy.util.RoamyUtils;
import com.roamy.util.RoamyValidationException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by Abhijit on 11/15/2015.
 */
@RestController
@RequestMapping("/users")
public class UserResource extends IdentityResource<User, Long> {

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
        String randomNumericString = RoamyUtils.generateRandomNumericString(6);
        entity.setVerificationCode(randomNumericString);
        entity.setVerificationCodeExpiry(new DateTime(new Date()).plusHours(2).toDate());
        entity.setIsVerified(false);

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
    public RestResponse activateUser(Long id, UserActionDto userActionDto) {

        if ("reset".equalsIgnoreCase(userActionDto.getAction())) {
            // 1. send new activation code

            // 2. update the status

        } else if ("activate".equalsIgnoreCase(userActionDto.getAction())) {
            // 1. find the user object

            // 2. check if the activation code matches

            // 3. update status of the user
        }
        return null;
    }
}
