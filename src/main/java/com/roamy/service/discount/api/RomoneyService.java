package com.roamy.service.discount.api;

import com.roamy.service.discount.dto.RomoneyDto;

/**
 * Created by Abhijit on 5/1/2016.
 */
public interface RomoneyService {

    RomoneyDto getRomoneyToApply(Long userId, String tripCode, Double bookingAmount);

    void creditRomoney(Long userId, Double amount, String comment);

    void debitRomoney(Long userId, Double amount, String comment);
}
