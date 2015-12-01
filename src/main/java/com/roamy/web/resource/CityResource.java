package com.roamy.web.resource;

import com.roamy.dao.api.CitableRepository;
import com.roamy.dao.api.CityRepository;
import com.roamy.dao.api.TripInstanceRepository;
import com.roamy.domain.*;
import com.roamy.dto.RestResponse;
import com.roamy.util.RestUtils;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Abhijit on 11/15/2015.
 */
@RestController
@RequestMapping("/cities")
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
    protected void validate(City entity) {

    }

    @Override
    protected void enrichForGet(City entity) {

    }

    @Override
    protected void enrichForSave(City entity) {

    }

    @Override
    protected void addLinks(City entity) {
        entity.getLinks().put("categories", "/" + entity.getCode() + "/categories");

    }

    @RequestMapping(value = "/{code}/categories", method = RequestMethod.GET)
    public RestResponse getCategories(@PathVariable String code) {

        RestResponse response = null;

        try {
            // TODO: add validations

            // 1. find all active trip instances for the city
            List<TripInstance> instances = tripInstanceRepository.findByTargetCitiesCodeAndStatus(code, Status.Active);

            // 2. find all categories of the trip instances
            Set<Category> categories = new HashSet<Category>();

            for (TripInstance instance : instances) {
                Trip trip = instance.getTrip();
                if (!CollectionUtils.isEmpty(trip.getCategories())) {
                    categories.addAll(trip.getCategories());
                }
            }
            LOGGER.info("categories for code({}): {}", code, categories);

            // return response
            response = new RestResponse(categories, HttpStatus.OK_200, null, null);

        } catch (Throwable t) {
            LOGGER.error("error in getCategories: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }
}
