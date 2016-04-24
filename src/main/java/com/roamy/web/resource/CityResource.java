package com.roamy.web.resource;

import com.roamy.dao.api.CitableRepository;
import com.roamy.dao.api.CityRepository;
import com.roamy.dao.api.TripRepository;
import com.roamy.domain.Category;
import com.roamy.domain.City;
import com.roamy.domain.Status;
import com.roamy.domain.Trip;
import com.roamy.dto.CityDto;
import com.roamy.dto.RestResponse;
import com.roamy.util.RestUtils;
import com.roamy.util.RoamyUtils;
import com.roamy.util.RoamyValidationException;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by Abhijit on 11/15/2015.
 */
@RestController
@RequestMapping("/cities")
@Api("city")
public class CityResource extends CitableResource<City, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CitableResource.class);

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private TripRepository tripRepository;

    @Override
    protected CitableRepository<City, Long> getCitableRepository() {
        return cityRepository;
    }

    @Override
    protected void enrichForGet(City entity) {

    }

    @Override
    protected void addLinks(City entity) {
        entity.get_links().put("categories", "/" + entity.getCode() + "/categories");

    }

    @RequestMapping(value = "/{code}/categories", method = RequestMethod.GET)
    @ApiOperation(value = "Get categories for the city", notes = "Fetches the categories that have trips scheduled for the city. " +
                            "Actual result is contained in the data field of the response.")
    public RestResponse getCategoriesForCity(@ApiParam(value = "City Code", required = true) @PathVariable String code) {

        RestResponse response = null;

        try {
            // TODO: add validations

            // 1. find all active trips with active instances in next 60 days for the city
            List<Trip> trips = tripRepository.findDistinctByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetween(Status.Active,
                    Arrays.asList(new String[]{code}),
                    Status.Active,
                    new Date(),
                    RoamyUtils.plusDays(new Date(), 60));

            // 2. find all categories of the trip instances
            Set<Category> categories = new HashSet<>();
            if (trips != null) {
                trips.forEach(trip -> {
                    if (!CollectionUtils.isEmpty(trip.getCategories())) {
                        categories.addAll(trip.getCategories());
                    }
                });
            }
            LOGGER.info("categories for code({}): {}", code, categories);

            // return response
            response = new RestResponse(categories, HttpStatus.OK_200, null, null);

        } catch (Throwable t) {
            LOGGER.error("error in getCategoriesForCity: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Create a City", notes = "Creates a City and returns the newly created entity. " +
            "Actual result is contained in the data field of the response.")
    public RestResponse createCity(@ApiParam(value = "City to be created in the JSON format sent as payload of the POST operation.",
                                            required = true)
                                  @RequestBody CityDto cityDto) {
        LOGGER.info("Saving: {}", cityDto);

        RestResponse response = null;

        try {
            // validate
            if (!StringUtils.hasText(cityDto.getCode())) {
                throw new RoamyValidationException("City code not provided");
            }
            if (!StringUtils.hasText(cityDto.getName())) {
                throw new RoamyValidationException("City name not provided");
            }

            // add missing information to the entity before it is saved
            City city = new City();
            city.setCode(cityDto.getCode());
            city.setName(cityDto.getName());
            city.setDescription(cityDto.getDescription());
            city.setStatus(Status.Active);
            RoamyUtils.addAuditPropertiesForCreateEntity(city, "test");

            // save the entity
            city = cityRepository.save(city);
            LOGGER.info("Entity Saved: {}", city);

            response = new RestResponse(city, HttpStatus.OK_200);

        } catch (Throwable t) {
            LOGGER.error("error in createCity: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }
}
