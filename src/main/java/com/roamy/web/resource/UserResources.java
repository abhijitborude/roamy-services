package com.roamy.web.resource;

import com.roamy.dao.api.UserRepository;
import com.roamy.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Abhijit on 11/15/2015.
 */
@RestController
@RequestMapping("/users")
public class UserResources extends AbstractResource<User, Long> {

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
    protected void enrich(User entity) {

    }

    @Override
    protected void addLinks(User entity) {

    }
}
