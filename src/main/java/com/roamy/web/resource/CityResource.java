package com.roamy.web.resource;

import com.roamy.dao.api.CitableRepository;
import com.roamy.dao.api.CityRepository;
import com.roamy.dao.api.TripInstanceRepository;
import com.roamy.domain.*;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private TripInstanceRepository tripInstanceRepository;

    @Override
    protected CitableRepository<City, Long> getCitableRepository() {
        return cityRepository;
    }

    @Override
    protected void validateForCreate(City entity) {
        if (!StringUtils.hasText(entity.getCode())) {
            throw new RoamyValidationException("City code not provided");
        }
        if (!StringUtils.hasText(entity.getName())) {
            throw new RoamyValidationException("City name not provided");
        }
    }

    @Override
    protected void enrichForGet(City entity) {

    }

    @Override
    protected void enrichForCreate(City entity) {
        if (entity.getStatus() == null) {
            entity.setStatus(Status.Active);
        }
        RoamyUtils.addAuditPropertiesForCreateEntity(entity, "test");
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

            // 1. find all active trip instances for the city
            List<TripInstance> instances = tripInstanceRepository.findByTargetCitiesCodeAndStatus(code, Status.Active);

            // 2. find all categories of the trip instances
            Set<Category> categories = new HashSet<>();

            if (instances != null) {
                instances.forEach(instance -> {
                    Trip trip = instance.getTrip();
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
    @ApiOperation(value = "Create a City", notes = "Creates a City and returns the created entity with it's ID. " +
            "Actual result is contained in the data field of the response.")
    public RestResponse createCity(@ApiParam(value = "City to be created in the JSON format sent as payload of the POST operation. " +
            "ID is not required and will be ignored.", required = true)
                                  @RequestBody City entity) {
        LOGGER.info("Saving: {}", entity);

        RestResponse response = null;

        try {
            // validate the incoming data
            validateForCreate(entity);

            // add missing information to the entity before it is saved
            enrichForCreate(entity);

            // save the entity
            City city = cityRepository.save(entity);
            LOGGER.info("Entity Saved: {}", city);

            response = new RestResponse(city, HttpStatus.OK_200);

        } catch (Throwable t) {
            LOGGER.error("error in createCity: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }
}
