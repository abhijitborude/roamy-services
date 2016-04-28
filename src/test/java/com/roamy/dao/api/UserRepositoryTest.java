package com.roamy.dao.api;

import com.roamy.TestApplication;
import com.roamy.domain.User;
import com.roamy.util.DomainObjectUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Abhijit on 10/14/2015.
 */
@ActiveProfiles("unit-test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
public class UserRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryTest.class);

    public static final String PHONE_NUMBER = "100";
    public static final String FNAME = "fname";

    private Long id;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() {
        User user = DomainObjectUtil.getUser(PHONE_NUMBER, "a@a.com", FNAME, "lname");
        user = userRepository.save(user);

        id = user.getId();
        LOGGER.info("User saved with id: " + id);
    }

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void testFindByUserId() {
        User user = userRepository.findOne(id);
        LOGGER.info("Found user {}", user);

        Assert.assertNotNull("There should be a user with id: " + id, user);
        Assert.assertEquals("User with id " + id + " should have first name " + FNAME, FNAME, user.getFirstName());
    }

    @Test
    public void testFindByPhoneNumber(){
        User user = userRepository.findByPhoneNumber(PHONE_NUMBER);
        LOGGER.info("Found user {}", user);

        Assert.assertNotNull("There should be a user with phone number: " + PHONE_NUMBER, user);
        Assert.assertEquals("User with phone number " + PHONE_NUMBER + " should have first name " + FNAME, FNAME, user.getFirstName());
    }
}