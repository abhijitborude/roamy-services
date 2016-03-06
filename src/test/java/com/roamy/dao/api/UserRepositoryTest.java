package com.roamy.dao.api;

import com.roamy.TestApplication;
import com.roamy.domain.AccountType;
import com.roamy.domain.Status;
import com.roamy.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by Abhijit on 10/14/2015.
 */
@ActiveProfiles("unit-test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@IntegrationTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() {

    }

    private User getUser(String phoneNumber, String email, String fname, String lname, String password) {
        User user = new User();
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        user.setFirstName(fname);
        user.setLastName(lname);
        user.setType(AccountType.Phone);
        user.setStatus(Status.Active);
        user.setCreatedBy("test");
        user.setCreatedOn(new Date());
        user.setLastModifiedBy("test");
        user.setLastModifiedOn(new Date());
        return user;
    }

    @Test
    public void testFindByPhoneNumber() throws Exception {
        User user = getUser("100", "a@a.com", "fname1", "lname1", "pass1");
        userRepository.save(user);
    }

    @Test
    public void testFindByEmail1() throws Exception {

    }
}