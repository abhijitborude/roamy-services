package com.roamy.web.resource;

import com.roamy.config.ConfigProperties;
import com.roamy.dao.api.ReservationPaymentRepository;
import com.roamy.dao.api.ReservationRepository;
import com.roamy.dao.api.TripInstanceRepository;
import com.roamy.dao.api.UserRepository;
import com.roamy.domain.*;
import com.roamy.dto.ReservationDto;
import com.roamy.dto.ReservationPaymentDto;
import com.roamy.dto.RestResponse;
import com.roamy.util.RestUtils;
import com.roamy.util.RoamyValidationException;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Abhijit on 12/13/2015.
 */
@RestController
@RequestMapping("/reservations")
public class ReservationResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationDto.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TripInstanceRepository tripInstanceRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationPaymentRepository reservationPaymentRepository;

    @Autowired
    private ConfigProperties configProperties;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public RestResponse createReservation(@RequestBody ReservationDto reservationDto) {
        LOGGER.info("Creating {}", reservationDto);

        RestResponse response = null;

        try {

            if (reservationDto == null) {
                throw new RoamyValidationException("Reservation information not provided");
            } else if (reservationDto.getUserId() == null) {
                throw new RoamyValidationException("User not provided");
            } else if (reservationDto.getTripInstanceId() == null) {
                throw new RoamyValidationException("Trip instance not provided");
            } else if (reservationDto.getNumberOfTravellers() <= 0) {
                throw new RoamyValidationException("Invalid number of travellers provided: " + reservationDto.getNumberOfTravellers());
            } else if (reservationDto.getAmount() == null || reservationDto.getAmount() <= 0) {
                throw new RoamyValidationException("Invalid amount: " + reservationDto.getAmount());
            } else if (!StringUtils.hasText(reservationDto.getEmail())) {
                throw new RoamyValidationException("Invalid email provided");
            } else if (!StringUtils.hasText(reservationDto.getPhoneNumber())) {
                throw new RoamyValidationException("Invalid phone number provided");
            }

            // load and validate user
            User user = userRepository.findOne(reservationDto.getUserId());
            if (user == null) {
                throw new RoamyValidationException("Invalid user");
            }

            // load and validate trip instance
            TripInstance tripInstance = tripInstanceRepository.findOne(reservationDto.getTripInstanceId());
            if (tripInstance == null) {
                throw new RoamyValidationException("Invalid trip instance");
            }

            Reservation reservation = new Reservation();
            reservation.setUser(user);
            reservation.setTripInstance(tripInstance);
            reservation.setNumberOfTravellers(reservationDto.getNumberOfTravellers());
            reservation.setAmount(reservationDto.getAmount());

            if (reservationDto.isUseRomoney() && user.getWalletBalance() != null) {

                Double romoneyPaymentAmount = 0d;

                // find amount that can be paid using romoney
                if (user.getWalletBalance() > configProperties.getMaxAllowedRomoney()) {
                    romoneyPaymentAmount = configProperties.getMaxAllowedRomoney();
                } else {
                    romoneyPaymentAmount = user.getWalletBalance();
                }

                // create a partial payment using romoney
                ReservationPayment payment = new ReservationPayment();
                payment.setAmount(romoneyPaymentAmount);
                payment.setType(PaymentType.Romoney);
                payment.setCreatedBy("test");
                payment.setLastModifiedBy("test");

                reservation.addPayment(payment);

                // reduce user's wallet balance
                user.setWalletBalance(user.getWalletBalance() - romoneyPaymentAmount);
            }

            // if email for reservation is not provided then fetch it from user
            if (StringUtils.hasText(reservationDto.getEmail())) {
                reservation.setEmail(reservationDto.getEmail());
            } else {
                reservation.setEmail(user.getEmail());
            }

            reservation.setPhoneNumber(reservationDto.getPhoneNumber());
            reservation.setStatus(Status.Pending);
            reservation.setCreatedBy("test");
            reservation.setLastModifiedBy("test");

            LOGGER.info("saving {}", reservation);
            Reservation savedReservation = reservationRepository.save(reservation);
            LOGGER.info("save complete");

            response = new RestResponse(savedReservation, HttpStatus.OK_200);

        } catch (Throwable t) {
            LOGGER.error("error in createReservation: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RestResponse getReservation(@PathVariable Long id) {
        LOGGER.info("Loading reservation with id ({})", id);

        RestResponse response = null;

        try {
            Reservation reservation = reservationRepository.findOne(id);
            LOGGER.info("Found {}", reservation);

            response = new RestResponse(reservation, HttpStatus.OK_200);
        } catch (Throwable t) {
            LOGGER.error("error in getReservation: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }

    @RequestMapping(value = "/{id}/payments/", method = RequestMethod.POST)
    public RestResponse createPayment(@PathVariable Long id, @RequestBody ReservationPaymentDto reservationPaymentDto) {
        LOGGER.info("Received payment {}", reservationPaymentDto);

        RestResponse response = null;

        try {
            Reservation reservation = reservationRepository.findOne(id);
            LOGGER.info("Found {}", reservation);

            // validate inputs
            if (reservation == null) {
                throw new RoamyValidationException("No reservation found with id: " + id);
            }

            PaymentType paymentType = PaymentType.findByText(reservationPaymentDto.getType());
            if (paymentType == null) {
                throw new RoamyValidationException("Invalid payment type: " + reservationPaymentDto.getType());
            }

            // create payment
            ReservationPayment payment = new ReservationPayment();
            payment.setReservation(reservation);
            payment.setAmount(reservationPaymentDto.getAmount());
            payment.setTransactionId(reservationPaymentDto.getTransactionId());
            payment.setType(paymentType);
            payment.setStatus(Status.Pending);
            payment.setCreatedBy("test");
            payment.setLastModifiedBy("test");

            reservationPaymentRepository.save(payment);
            reservationPaymentRepository.flush();

            reservation.getPayments().add(payment);
            reservation.setStatus(Status.Active);
            reservationRepository.save(reservation);
            reservationRepository.flush();

            // capture payment


            response = new RestResponse(reservation, HttpStatus.OK_200);
        } catch (Throwable t) {
            LOGGER.error("error in getReservation: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }
}
