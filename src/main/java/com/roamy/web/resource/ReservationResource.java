package com.roamy.web.resource;

import com.roamy.dao.api.ReservationRepository;
import com.roamy.dao.api.TripInstanceRepository;
import com.roamy.dao.api.UserRepository;
import com.roamy.domain.Reservation;
import com.roamy.domain.Status;
import com.roamy.domain.TripInstance;
import com.roamy.domain.User;
import com.roamy.dto.ReservationDto;
import com.roamy.dto.RestResponse;
import com.roamy.util.RestUtils;
import com.roamy.util.RoamyValidationException;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
            reservation.setEmail(reservationDto.getEmail());
            reservation.setPhoneNumber(reservationDto.getPhoneNumber());
            reservation.setStatus(Status.Active);
            reservation.setCreatedBy("test");
            reservation.setLastModifiedBy("test");

            LOGGER.info("saving {}", reservation);
            reservationRepository.save(reservation);
            LOGGER.info("save complete");

        } catch (Throwable t) {
            LOGGER.error("error in createReservation: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }
}
