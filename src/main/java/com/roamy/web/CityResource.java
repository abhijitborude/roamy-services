package com.roamy.web;

import com.roamy.domain.City;
import com.roamy.service.api.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abhijit on 10/14/2015.
 */
@RestController
@RequestMapping(value = "/cities")
public class CityResource {

    private static final Logger logger = LoggerFactory.getLogger(CityResource.class);

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/{cityId}", method = RequestMethod.GET)
    public City getCity(@PathVariable Long cityId) {

        City city = cityService.getCityById(cityId);
        logger.info("Found {}", city);

        return city;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<City> getAllCities() {

        List<City> cities = cityService.getAllActiveCities();
        logger.info("Found {} cities", cities == null ? 0 : cities.size());

        return cities;
    }
}
