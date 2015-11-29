package com.roamy.web.resource;

import com.roamy.dao.api.CitableRepository;
import com.roamy.dao.api.TripInstanceRepository;
import com.roamy.dao.api.TripRepository;
import com.roamy.domain.Status;
import com.roamy.domain.Trip;
import com.roamy.domain.TripInstance;
import com.roamy.dto.RestResponse;
import com.roamy.util.RestUtils;
import org.eclipse.jetty.http.HttpStatus;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Abhijit on 11/15/2015.
 */
@RestController
@RequestMapping("/trips")
public class TripResource extends CitableResource<Trip, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripResource.class);

    private static final String SORT_BY_DIFFICULTY = "difficulty";
    private static final String SORT_BY_PRICE = "price";

    private static final String SORT_TYPE_ASC = "asc";
    private static final String SORT_TYPE_DESC = "desc";

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TripInstanceRepository tripInstanceRepository;

    @Override
    protected CitableRepository<Trip, Long> getCitableRepository() {
        return tripRepository;
    }

    @Override
    protected void validate(Trip entity) {

    }

    @Override
    protected void enrich(Trip entity) {

    }

    @Override
    protected void addLinks(Trip entity) {

    }

    @RequestMapping(value = "/{code}/activeinstances", method = RequestMethod.GET)
    public RestResponse findByTripCodeAndStatus(@PathVariable("code") String code) {

        RestResponse response = null;

        try {
            List<TripInstance> tripInstances = tripInstanceRepository.findByTripCodeAndStatus(code, Status.Active);
            LOGGER.info("active Trip Instances by code({}): {}", code, tripInstances);

            response = new RestResponse(tripInstances, HttpStatus.OK_200, null, null);

        } catch (Throwable t) {
            LOGGER.error("error in finding activeinstances: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;

    }

    @RequestMapping(value = "/listing", method = RequestMethod.GET)
    public RestResponse getTripsWithActiveInstanceBetweenDates(@RequestParam(value = "categoryCode", required = false) String categoryCode,
                                                               @RequestParam(value = "startDate", required = false) String startDate,
                                                               @RequestParam(value = "endDate", required = false) String endDate,
                                                               @RequestParam(value = "sortBy", required = false) String sortBy,
                                                               @RequestParam(value = "sortType", required = false) String sortType) {

        LOGGER.info("Finding trip listing for category ({}) from {} to {} and sorted by {} ({})", categoryCode, startDate, endDate, sortBy, sortType);

        RestResponse response = null;

        try {
            // TODO: add validations and optimize queries below

            DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-DD-MM");

            // convert start date
            Date sDate = new Date();

            if (StringUtils.hasText(startDate)) {
                DateTime dateTime = dtf.parseDateTime(startDate);
                if (dateTime != null) {
                    sDate = dateTime.toDate();
                }
            }

            // convert end date
            Date eDate = new Date();
            if (StringUtils.hasText(endDate)) {
                DateTime dateTime = dtf.parseDateTime(endDate);
                if (dateTime != null) {
                    eDate = dateTime.toDate();
                }
            } else {
                eDate = new DateTime(eDate).plusDays(30).toDate();
            }

            // TODO: instead of if-else statements, use reflection to construct method name and invoke on the bean

            List<Trip> trips = new ArrayList<Trip>();

            // 1. find active trips with instances that are active and have date between start and end date
            if (!StringUtils.hasText(categoryCode)) {
                if (SORT_BY_DIFFICULTY.equalsIgnoreCase(sortBy)) {
                    if (SORT_TYPE_DESC.equals(sortType)) {
                        trips = tripRepository.findByStatusAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultDesc(Status.Active, Status.Active, sDate, eDate);
                    } else {
                        trips = tripRepository.findByStatusAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultAsc(Status.Active, Status.Active, sDate, eDate);
                    }

                } else if (SORT_BY_PRICE.equalsIgnoreCase(sortBy)) {
                    if (SORT_TYPE_DESC.equals(sortType)) {
                        trips = tripRepository.findByStatusAndInstancesStatusAndInstancesDateBetweenOrderByDifficultyLevelDesc(Status.Active, Status.Active, sDate, eDate);
                    } else {
                        trips = tripRepository.findByStatusAndInstancesStatusAndInstancesDateBetweenOrderByDifficultyLevelAsc(Status.Active, Status.Active, sDate, eDate);
                    }

                } else {
                    trips = tripRepository.findByStatusAndInstancesStatusAndInstancesDateBetween(Status.Active, Status.Active, sDate, eDate);
                }
            } else {
                if (SORT_BY_DIFFICULTY.equalsIgnoreCase(sortBy)) {
                    if (SORT_TYPE_DESC.equals(sortType)) {
                        trips = tripRepository.findByStatusAndCategoriesCodeAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultDesc(Status.Active, categoryCode, Status.Active, sDate, eDate);
                    } else {
                        trips = tripRepository.findByStatusAndCategoriesCodeAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultAsc(Status.Active, categoryCode, Status.Active, sDate, eDate);
                    }

                } else if (SORT_BY_PRICE.equalsIgnoreCase(sortBy)) {
                    if (SORT_TYPE_DESC.equals(sortType)) {
                        trips = tripRepository.findByStatusAndCategoriesCodeAndInstancesStatusAndInstancesDateBetweenOrderByDifficultyLevelDesc(Status.Active, categoryCode, Status.Active, sDate, eDate);
                    } else {
                        trips = tripRepository.findByStatusAndCategoriesCodeAndInstancesStatusAndInstancesDateBetweenOrderByDifficultyLevelAsc(Status.Active, categoryCode, Status.Active, sDate, eDate);
                    }

                } else {
                    trips = tripRepository.findByStatusAndCategoriesCodeAndInstancesStatusAndInstancesDateBetween(Status.Active, categoryCode, Status.Active, sDate, eDate);
                }
            }

            LOGGER.info("number of trips in the listing: {}", trips == null ? 0 : trips.size());

            // return response
            response = new RestResponse(trips, HttpStatus.OK_200, null, null);

        } catch (Throwable t) {
            LOGGER.error("error in getting active trips listing: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }
}
