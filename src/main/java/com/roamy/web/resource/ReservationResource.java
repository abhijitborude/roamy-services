package com.roamy.web.resource;

import com.roamy.config.ConfigProperties;
import com.roamy.dao.api.*;
import com.roamy.domain.*;
import com.roamy.dto.*;
import com.roamy.integration.paymentGateway.dto.PaymentDto;
import com.roamy.integration.paymentGateway.service.api.PaymentGatewayService;
import com.roamy.service.notification.api.EmailNotificationService;
import com.roamy.service.notification.api.SmsNotificationService;
import com.roamy.util.RestUtils;
import com.roamy.util.RoamyValidationException;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
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
@Api("reservation")
public class ReservationResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationResource.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TripInstanceRepository tripInstanceRepository;

    @Autowired
    private TripInstanceOptionRepository tripInstanceOptionRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationPaymentRepository reservationPaymentRepository;

    @Autowired
    private ReservationTripOptionRepository reservationTripOptionRepository;

    @Autowired
    @Qualifier("razorpayGatewayService")
    private PaymentGatewayService razorpayGatewayService;

    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Autowired
    private SmsNotificationService smsNotificationService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROAMY') or hasRole('ADMIN')")
    @ApiOperation(value = "Create a reservation", notes = "Creates reservation for a user, tripInstance and " +
            "tripInstanceOptions. TripInstanceOptions provide selections made by the user (e.g. regular vs premium, " +
            "adult and senior). useRomoney flag can be set to true to use Romoney in user's account and apply it " +
            "towards this reservation. Email and Phone number are mandatory." +
            " Actual result is contained in the data field of the response.")
    public RestResponse createReservation(@ApiParam(name = "reservationDetails", value = "Reservation Details. " +
                                                    "All fields are mandatory", required = true)
                                              @RequestBody ReservationDto reservationDto) {
        LOGGER.info("Creating {}", reservationDto);

        RestResponse response = null;

        try {

            if (reservationDto == null) {
                throw new RoamyValidationException("Reservation information not provided");
            }
            else if (reservationDto.getUserId() == null) {
                throw new RoamyValidationException("User not provided");
            }
            else if (reservationDto.getTripInstanceId() == null) {
                throw new RoamyValidationException("Trip instance not provided");
            }
            else if (CollectionUtils.isEmpty(reservationDto.getReservationTripOptions())) {
                throw new RoamyValidationException("Trip option reservation not provided");
            }
            else if (!StringUtils.hasText(reservationDto.getEmail())) {
                throw new RoamyValidationException("Invalid email provided");
            }
            else if (!StringUtils.hasText(reservationDto.getPhoneNumber())) {
                throw new RoamyValidationException("Invalid phone number provided");
            }

            // load and validate user provided
            User user = userRepository.findOne(reservationDto.getUserId());
            if (user == null) {
                throw new RoamyValidationException("Invalid user");
            }

            // load and validate trip instance provided
            TripInstance tripInstance = tripInstanceRepository.findOne(reservationDto.getTripInstanceId());
            if (tripInstance == null) {
                throw new RoamyValidationException("Invalid trip instance");
            }

            // 1. validate tripInstanceOptionId, count and create ReservationTripOptions
            List<ReservationTripOption> reservationTripOptions = new ArrayList<>();

            reservationDto.getReservationTripOptions().forEach((optionDto) -> {
                if (optionDto.getTripInstanceOptionId() == null || optionDto.getTripInstanceOptionId() < 0) {
                    throw new RoamyValidationException("Invalid trip instance option id provided: " + optionDto.getTripInstanceOptionId());
                }

                TripInstanceOption tripInstanceOption = tripInstanceOptionRepository.findOne(optionDto.getTripInstanceOptionId());
                if (tripInstanceOption == null) {
                    throw new RoamyValidationException("Invalid trip instance option id provided: " + optionDto.getTripInstanceOptionId());
                }

                // check whether this tripInstanceOption supports age based pricing or not
                boolean ageBasedPricing = tripInstanceOption.isAgeBasedPricing();

                ReservationTripOption option = new ReservationTripOption();
                option.setTripInstanceOption(tripInstanceOption);
                option.setAgeBasedPricing(ageBasedPricing);
                boolean validCount = false;

                if (ageBasedPricing) {
                    if ((optionDto.getAdultCount() == null || optionDto.getAdultCount() < 0)
                        && (optionDto.getSeniorCount() == null || optionDto.getSeniorCount() < 0)
                        && (optionDto.getChildCount() == null || optionDto.getChildCount() < 0)) {
                        throw new RoamyValidationException("Invalid age based reservation counts provided");
                    }

                    if ((optionDto.getAdultCount() != null && optionDto.getAdultCount() > 0)
                            || (optionDto.getSeniorCount() != null && optionDto.getSeniorCount() > 0)
                            || (optionDto.getChildCount() != null && optionDto.getChildCount() > 0)) {
                        validCount = true;
                        option.setAdultCount(optionDto.getAdultCount() == null ? 0 : optionDto.getAdultCount());
                        option.setSeniorCount(optionDto.getSeniorCount() == null ? 0 : optionDto.getSeniorCount());
                        option.setChildCount(optionDto.getChildCount() == null ? 0 : optionDto.getChildCount());
                    }
                }
                else {
                    if (optionDto.getCount() == null || optionDto.getCount() < 0) {
                        throw new RoamyValidationException("Invalid count provided: " + optionDto.getCount());
                    }

                    if (optionDto.getCount() != null && optionDto.getCount() > 0) {
                        validCount = true;
                        option.setCount(optionDto.getCount());
                    }
                }

                if (validCount) {
                    reservationTripOptions.add(option);
                }
            });

            if (CollectionUtils.isEmpty(reservationTripOptions)) {
                throw new RoamyValidationException("Invalid counts provided. Please select at least one trip option.");
            }

            // now create a reservation using the data gathered so far
            Reservation reservation = new PackageReservation();
            reservation.setUser(user);

            List<TripInstance> instances = new ArrayList<>();
            instances.add(tripInstance);
            reservation.setTripInstances(instances);

            // find total amount
            Double totalAmount = 0d;
            for (ReservationTripOption option : reservationTripOptions) {
                totalAmount += option.getCount() * option.getTripInstanceOption().getPrice();
            }
            reservation.setAmount(totalAmount);
            reservation.setStartDate(tripInstance.getDate());

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

            reservation.setTripOptions(reservationTripOptions);


            // apply romoney
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

            LOGGER.info("saving {}", reservation);
            reservation = reservationRepository.save(reservation);

            LOGGER.info("saving {}", user);
            userRepository.save(user);
            LOGGER.info("save complete");

            // send email
            emailNotificationService.sendTripReservationEmail(reservation);

            response = new RestResponse(reservation, HttpStatus.OK_200);

        } catch (Throwable t) {
            LOGGER.error("error in createReservation: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROAMY') or hasRole('ADMIN')")
    @ApiOperation(value = "Get reservation By id", notes = "Fetches reservation details for a given ID. " +
                            "Actual result is contained in the data field of the response.")
    public RestResponse getReservation(@ApiParam(value = "Reservation ID", required = true)
                                           @PathVariable Long id) {
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
    @PreAuthorize("hasRole('ROAMY') or hasRole('ADMIN')")
    @ApiOperation(value = "Create a payment", notes = "Creates payment for a Reservation represented by given " +
                        "Reservation ID. Actual result is contained in the data field of the response.")
    public RestResponse createPayment(@ApiParam(name = "reservationId", value = "Reservation ID for which payment is being made", required = true)
                                            @PathVariable Long id,
                                      @ApiParam(name = "paymentDetails", value = "Payment Details including amount, type " +
                                              "[Razorpay/Discount/Romoney], and transactionID [Razorpay transaction ID " +
                                              "when type is Razorpay]", required = true)
                                            @RequestBody ReservationPaymentDto reservationPaymentDto) {
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
            payment.setAmount(reservationPaymentDto.getAmount());
            payment.setTransactionId(reservationPaymentDto.getTransactionId());
            payment.setType(paymentType);
            payment.setStatus(Status.Pending);
            payment.setCreatedBy("test");
            payment.setLastModifiedBy("test");

            // capture payment
            PaymentDto paymentDto = razorpayGatewayService.capturePayment(reservationPaymentDto.getTransactionId(), reservationPaymentDto.getAmount());
            if ("captured".equals(paymentDto.getStatus())) {
                payment.setStatus(Status.Success);
                reservation.setStatus(Status.Active);
            } else {
                payment.setStatus(Status.Failed);
                reservation.setStatus(Status.Pending);
            }

            reservation.getPayments().add(payment);
            reservationRepository.save(reservation);

            response = new RestResponse(reservation, HttpStatus.OK_200);
        } catch (Throwable t) {
            LOGGER.error("error in getReservation: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }

    @RequestMapping(value = "/{id}/sharebysms/", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROAMY') or hasRole('ADMIN')")
    @ApiOperation(value = "share reservation with friends via sms",
                    notes = "Sends an sms to the friend provided in the request body.")
    public RestResponse shareReservationBySms(@ApiParam(name = "reservationId", value = "ID of the reservation being shared", required = true)
                                                  @PathVariable Long id,
                                              @ApiParam(name = "friends", value = "List of friend details. Each friend should have a name and phone number.", required = true)
                                                  @RequestBody ShareRservationRequest shareRequest) {

        LOGGER.info("sharing reservation ({}) details with {}", id, shareRequest);

        RestResponse response = null;
        try {
            Reservation reservation = reservationRepository.findOne(id);
            shareRequest.getFriends().forEach(friendInfo ->
                smsNotificationService.sendTripReservationShareSms(reservation, friendInfo.getName(), friendInfo.getPhoneNumber())
            );
        } catch (Throwable t) {
            LOGGER.error("error in shareReservationBySms: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }
}
