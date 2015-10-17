package com.roamy.service.api;

import com.roamy.domain.User;
/**
 * Created by Abhijit on 10/17/2015.
 */
public interface UserService {

    User getUserByPhoneNumber(String phoneNumber);

    User getUserByEmail(String email);

    User createUser(User user);

    User updateUser(User user);
}
