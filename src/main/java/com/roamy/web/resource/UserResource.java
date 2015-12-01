package com.roamy.web.resource;

import com.roamy.dao.api.UserRepository;
import com.roamy.domain.User;
import com.roamy.util.RoamyUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by Abhijit on 11/15/2015.
 */
@RestController
@RequestMapping("/users")
public class UserResource extends AbstractResource<User, Long> {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected JpaRepository<User, Long> getJpaRepository() {
        return userRepository;
    }

    @Override
    protected void validate(User entity) {

    }

    @Override
    protected void enrichForGet(User entity) {

    }

    @Override
    protected void enrichForCreate(User entity) {
        String randomNumericString = RoamyUtils.generateRandomNumericString(6);
        entity.setVerificationCode(randomNumericString);
        entity.setVerificationCodeExpiry(new DateTime(new Date()).plusHours(2).toDate());
        entity.setIsVerified(false);
    }

    @Override
    protected void afterEntityCreated(User entity) {
        // send sms with verification code
    }

    @Override
    protected void addLinks(User entity) {

    }
}
