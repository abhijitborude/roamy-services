package com.roamy.service.discount.impl;

import com.roamy.dao.api.TripRepository;
import com.roamy.dao.api.UserRepository;
import com.roamy.dao.api.WalletTransactionRepository;
import com.roamy.domain.Trip;
import com.roamy.domain.User;
import com.roamy.domain.WalletTransaction;
import com.roamy.service.discount.api.RomoneyService;
import com.roamy.service.discount.dto.RomoneyDto;
import com.roamy.util.RoamyValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Abhijit on 5/1/2016.
 */
@Service("romoneyService")
public class RomoneyServiceImpl implements RomoneyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RomoneyServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    @Autowired
    private TripRepository tripRepository;

    @Override
    public RomoneyDto getRomoneyToApply(Long userId, String tripCode, Double bookingAmount) {
        LOGGER.info("calculating romoney that can be applied for userId({}), tripCode({}) and bookingAmount({})", userId, tripCode, bookingAmount);
        User user = userRepository.findOne(userId);
        if (user == null) {
            throw new RoamyValidationException("User with id " + userId + " not found");
        }

        Trip trip = tripRepository.findByCode(tripCode);
        if (trip == null) {
            throw new RoamyValidationException("Trip with code " + tripCode + " not found");
        }

        int romoneyPercentage = trip.getRomoneyPercentage();
        Double maxRomoneyAmount = new Integer(romoneyPercentage).doubleValue() * bookingAmount / 100;

        Double romoneyAmountToApply = 0d;
        if (user.getWalletBalance() != null) {
            if (user.getWalletBalance() >= maxRomoneyAmount) {
                romoneyAmountToApply = maxRomoneyAmount;
            } else {
                LOGGER.info("User({}) does NOT have enough walletBalance({}) to apply {}% romoney({}) towards bookingAmount({})",
                                userId, user.getWalletBalance(), romoneyPercentage, maxRomoneyAmount, bookingAmount);
                romoneyAmountToApply = user.getWalletBalance();
            }
        }

        RomoneyDto dto = new RomoneyDto(userId, tripCode, bookingAmount);
        dto.setUserWalletBalance(user.getWalletBalance());
        dto.setRomoneyAmountToApply(romoneyAmountToApply);

        LOGGER.info("returning {}", dto);
        return dto;
    }

    @Override
    public void creditRomoney(Long userId, Double amount, String comment) {
        LOGGER.info("crediting Romoney amount({}) to userId({}) with comment({})", userId, amount, comment);

        User user = userRepository.findOne(userId);
        if (user == null) {
            throw new RoamyValidationException("User with id " + userId + " not found");
        }

        Double walletBalance = user.getWalletBalance();
        if (walletBalance == null) {
            walletBalance = 0d;
        }

        WalletTransaction transaction = walletTransactionRepository.save(new WalletTransaction(user, amount, comment));
        LOGGER.info("Saved {}", transaction);

        walletBalance += amount;
        user.setWalletBalance(walletBalance);
        LOGGER.info("Saved new walletBalance({}) for {}", walletBalance, user);
    }

    @Override
    public void debitRomoney(Long userId, Double amount, String comment) {
        LOGGER.info("debiting Romoney amount({}) to userId({}) with comment({})", userId, amount, comment);

        User user = userRepository.findOne(userId);
        if (user == null) {
            throw new RoamyValidationException("User with id " + userId + " not found");
        }

        Double walletBalance = user.getWalletBalance();
        if (walletBalance == null || walletBalance < amount) {
            throw new RoamyValidationException(user + " does not have enough walletBalance to debit Romoney(" + amount + ")");
        }

        WalletTransaction transaction = walletTransactionRepository.save(new WalletTransaction(user, -1 * amount, comment));
        LOGGER.info("Saved {}", transaction);

        walletBalance -= amount;
        user.setWalletBalance(walletBalance);
        LOGGER.info("Saved new walletBalance({}) for {}", walletBalance, user);
    }
}
