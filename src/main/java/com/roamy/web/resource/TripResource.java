package com.roamy.web.resource;

import com.roamy.dao.api.CitableRepository;
import com.roamy.dao.api.TripInstanceRepository;
import com.roamy.dao.api.TripRepository;
import com.roamy.dao.api.TripReviewRepository;
import com.roamy.domain.Status;
import com.roamy.domain.Trip;
import com.roamy.domain.TripInstance;
import com.roamy.domain.TripReview;
import com.roamy.dto.RestResponse;
import com.roamy.util.RestUtils;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.eclipse.jetty.http.HttpStatus;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by Abhijit on 11/15/2015.
 */
@RestController
@RequestMapping("/trips")
@Api("trip")
public class TripResource extends CitableResource<Trip, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripResource.class);

    private static final String SORT_BY_DIFFICULTY = "difficulty";
    private static final String SORT_BY_PRICE = "price";

    private static final String SORT_TYPE_DESC = "desc";

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TripInstanceRepository tripInstanceRepository;

    @Autowired
    private TripReviewRepository tripReviewRepository;

    @Override
    protected CitableRepository<Trip, Long> getCitableRepository() {
        return tripRepository;
    }

    @Override
    protected void enrichForGet(Trip entity) {

    }

    @Override
    protected void addLinks(Trip entity) {

    }

    @RequestMapping(value = "/{code}/activeinstances", method = RequestMethod.GET)
    @ApiOperation(value = "Get active instances of a trip", notes = "Fetches list of active trip instances for a trip. " +
                            "Actual result is contained in the data field of the response.")
    public RestResponse findActiveTripInstanes(@ApiParam(value = "Trip Code", required = true)
                                               @PathVariable("code") String code,
                                               @ApiParam(value = "Trip Instance Date in the format 'dd-MM-yyyy'", required = false)
                                               @RequestParam(value = "date", required = false) String sDate) {

        RestResponse response = null;

        try {
            DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");

            // convert date
            Date date = new Date();
            if (StringUtils.hasText(sDate)) {
                date = dtf.parseDateTime(sDate).toDate();
            }

            List<TripInstance> tripInstances = null;
            if (sDate == null) {
                tripInstances = tripInstanceRepository.findTop60ByTripCodeAndStatus(code, Status.Active);
                LOGGER.info("active Trip Instances by code({}): {}", code, tripInstances);
            } else {
                tripInstances = tripInstanceRepository.findByTripCodeAndDateAndStatus(code, date, Status.Active);
                LOGGER.info("active Trip Instances by code({}) and date ({} - {}): {}", code, sDate, date, tripInstances);
            }

            response = new RestResponse(tripInstances, HttpStatus.OK_200);

        } catch (Throwable t) {
            LOGGER.error("error in finding activeinstances: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }

    @RequestMapping(value = "/listing", method = RequestMethod.GET)
    @ApiOperation(value = "Get trip listing", notes = "Fetches list of trips that have active instances between given " +
                        "start and end date for a city and category. List could be sorted in ascending or descending " +
                        "order using the fields sortBy and sortType. " +
                        "Actual result is contained in the data field of the response.")
    //@Cacheable("tripListing")
    public RestResponse getTripListing(
                                @ApiParam(value = "City Code", required = false)
                                    @RequestParam(value = "cityCode", required = false) String cityCode,
                                @ApiParam(value = "Category Code", required = false)
                                    @RequestParam(value = "categoryCode", required = false) String categoryCode,
                                @ApiParam(value = "Start Date in the format 'dd-MM-yyyy' (Defaulted to current date)", required = false)
                                    @RequestParam(value = "startDate", required = false) String startDate,
                                @ApiParam(value = "End Date in the format 'dd-MM-yyyy' (Defaulted to 30 days from current date)", required = false)
                                    @RequestParam(value = "endDate", required = false) String endDate,
                                @ApiParam(value = "Sort By [price/difficulty]", required = false)
                                    @RequestParam(value = "sortBy", required = false) String sortBy,
                                @ApiParam(value = "Sort Type [asc/desc]", required = false)
                                    @RequestParam(value = "sortType", required = false) String sortType) {

        LOGGER.info("Finding trip listing for category ({}) from {} to {} and sorted by {} ({})", categoryCode, startDate, endDate, sortBy, sortType);

        RestResponse response = null;

        try {
            // TODO: add validations and optimize queries below

            DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");

            // convert start date
            Date sDate = new Date();
            if (StringUtils.hasText(startDate)) {
                sDate = dtf.parseDateTime(startDate).toDate();
            }

            // Convert end date. If not provided set to 30 days from current date.
            Date eDate = new Date();
            if (StringUtils.hasText(endDate)) {
                eDate = dtf.parseDateTime(endDate).toDate();
            } else {
                eDate = new DateTime(eDate).plusDays(30).toDate();
            }

            // create city code list
            List<String> cityCodes = new ArrayList<>();
            cityCodes.add("ALL");
            if (StringUtils.hasText(cityCode)) {
                cityCodes.add(cityCode);
            }

            // create category code list
            List<String> categoryCodes = new ArrayList<>();
            if (StringUtils.hasText(categoryCode)) {
                categoryCodes.add(categoryCode);
            }

            // TODO: instead of if-else statements, use reflection to construct method name and invoke on the bean

            List<Trip> trips = new ArrayList<>();

            // 1. find active trips with instances that are active and have date between start and end date
            if (CollectionUtils.isEmpty(categoryCodes)) {
                if (SORT_BY_PRICE.equalsIgnoreCase(sortBy)) {
                    if (SORT_TYPE_DESC.equals(sortType)) {
                        trips = tripRepository.findDistinctByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultDesc(Status.Active, cityCodes, Status.Active, sDate, eDate);
                    } else {
                        trips = tripRepository.findDistinctByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultAsc(Status.Active, cityCodes, Status.Active, sDate, eDate);
                    }

                } else if (SORT_BY_DIFFICULTY.equalsIgnoreCase(sortBy)) {
                    if (SORT_TYPE_DESC.equals(sortType)) {
                        trips = tripRepository.findDistinctByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByThrillMeterDesc(Status.Active, cityCodes, Status.Active, sDate, eDate);
                    } else {
                        trips = tripRepository.findDistinctByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByThrillMeterAsc(Status.Active, cityCodes, Status.Active, sDate, eDate);
                    }

                } else {
                    trips = tripRepository.findDistinctByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetween(Status.Active, cityCodes, Status.Active, sDate, eDate);
                }
            } else {
                if (SORT_BY_PRICE.equalsIgnoreCase(sortBy)) {
                    if (SORT_TYPE_DESC.equals(sortType)) {
                        trips = tripRepository.findDistinctByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultDesc(Status.Active, cityCodes, categoryCodes, Status.Active, sDate, eDate);
                    } else {
                        trips = tripRepository.findDistinctByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultAsc(Status.Active, cityCodes, categoryCodes, Status.Active, sDate, eDate);
                    }

                } else if (SORT_BY_DIFFICULTY.equalsIgnoreCase(sortBy)) {
                    if (SORT_TYPE_DESC.equals(sortType)) {
                        trips = tripRepository.findDistinctByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByThrillMeterDesc(Status.Active, cityCodes, categoryCodes, Status.Active, sDate, eDate);
                    } else {
                        trips = tripRepository.findDistinctByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByThrillMeterAsc(Status.Active, cityCodes, categoryCodes, Status.Active, sDate, eDate);
                    }

                } else {
                    trips = tripRepository.findDistinctByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetween(Status.Active, cityCodes, categoryCodes, Status.Active, sDate, eDate);
                }
            }

            // hack- remove duplicates by using a Set
            Set<Trip> tripSet = new HashSet<>(trips);
            LOGGER.info("number of trips in the listing: {}", tripSet.size());

            // return response
            response = new RestResponse(tripSet, HttpStatus.OK_200, null, null);

        } catch (Throwable t) {
            LOGGER.error("error in getting active trips listing: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }

    @RequestMapping(value = "/{code}/reviews", method = RequestMethod.GET)
    @ApiOperation(value = "Get trip reviews", notes = "Fetches list of trip reviews for a trip. " +
                            "Actual result is contained in the data field of the response.")
    public RestResponse findReviewsByTripCode(@ApiParam(value = "Trip Code", required = true)
                                                    @PathVariable("code") String code) {

        RestResponse response = null;

        try {
            List<TripReview> reviews = tripReviewRepository.findByTripCode(code);
            LOGGER.info("Number of reviews found: {}", reviews == null ? 0 : reviews.size());

            response = new RestResponse(reviews, HttpStatus.OK_200, null, null);

        } catch (Throwable t) {
            LOGGER.error("error while finding reviews: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }
}
