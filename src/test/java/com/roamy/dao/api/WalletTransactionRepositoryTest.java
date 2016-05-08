package com.roamy.dao.api;

import com.roamy.TestApplication;
import com.roamy.domain.User;
import com.roamy.domain.WalletTransaction;
import com.roamy.util.DomainObjectUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Abhijit on 5/1/2016.
 */
@ActiveProfiles("unit-test")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Rollback
@SpringApplicationConfiguration(classes = TestApplication.class)
public class WalletTransactionRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(WalletTransactionRepositoryTest.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    private Long userId;

    @Before
    public void Setup() {
        User user = DomainObjectUtil.createUser("400", "a@a.com", "fname", "lname");
        user = userRepository.save(user);

        userId = user.getId();
        LOGGER.info("User saved with id: " + userId);

        WalletTransaction t1 = DomainObjectUtil.createWalletTransaction(user, 500.0, "t1");
        walletTransactionRepository.save(t1);

        WalletTransaction t2 = DomainObjectUtil.createWalletTransaction(user, -500.0, "t2");
        walletTransactionRepository.save(t2);

        WalletTransaction t3 = DomainObjectUtil.createWalletTransaction(user, 1500.0, "t2");
        walletTransactionRepository.save(t3);
    }

    @Test
    public void testFindByUserId() throws Exception {
        List<WalletTransaction> transactions = walletTransactionRepository.findByUserId(userId);
        LOGGER.info("Transactions found: {}", transactions);
        Assert.assertEquals("There should be 3 transactions", 3, transactions.size());
    }
}