package com.roamy.dao.api;

import com.roamy.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Abhijit on 10/14/2015.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByPhoneNumber(String phoneNumber);

    User findByEmail(String email);
}
