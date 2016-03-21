package com.roamy.service.security;

import com.roamy.dao.api.UserRepository;
import com.roamy.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Abhijit on 3/20/16.
 */
@Service
public class RoamyUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoamyUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LOGGER.info("authenticating {}", username);
        final User user = userRepository.findByPhoneNumber(username);
        if (user == null) {
            throw new UsernameNotFoundException("user with phone number " + username + " not found");
        }
        LOGGER.info("founds {}", user);

        return new org.springframework.security.core.userdetails.User(
                    username,
                    user.getToken(),
                    Arrays.asList(new GrantedAuthority() {
                        @Override
                        public String getAuthority() {
                            return user.getType().name();
                        }
                    })
        );
    }
}
