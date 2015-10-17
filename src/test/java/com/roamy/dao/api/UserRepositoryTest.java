package com.roamy.dao.api;

import com.roamy.TestApplication;
import com.roamy.domain.AccountType;
import com.roamy.domain.Status;
import com.roamy.domain.User;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Abhijit on 10/14/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@IntegrationTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() {
        User user = getUser("100", "a@a.com", "fname1", "lname1", "pass1");
        userRepository.save(user);

        user = getUser("200", "b@b.com", "fname2", "lname2", "pass2");
        userRepository.save(user);

    }

    private User getUser(String phoneNumber, String email, String fname, String lname, String password) {
        User user = new User();
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        user.setFirstName(fname);
        user.setLastName(lname);
        user.setType(AccountType.Email);
        user.setStatus(Status.Active);
        user.setPassword(password);
        user.setCreatedBy("test");
        user.setCreatedOn(new Date());
        user.setLastModifiedBy("test");
        user.setLastModifiedOn(new Date());
        return user;
    }

    @Test
    public void testFindByPhoneNumber() throws Exception {

    }

    @Test
    public void testFindByEmail1() throws Exception {

    }
}