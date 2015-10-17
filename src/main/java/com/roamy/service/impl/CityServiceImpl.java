package com.roamy.service.impl;

import com.roamy.dao.api.CityRepository;
import com.roamy.domain.City;
import com.roamy.domain.Status;
import com.roamy.service.api.CityService;
import com.roamy.util.RoamyValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by Abhijit on 10/11/2015.
 */
@Service
@Transactional
public class CityServiceImpl implements CityService {

    private Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);

    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<City> getAllCities() {
        List<City> cities = cityRepository.findAll();
        logger.info("Number of cities found: {}", cities == null ? 0 : cities.size());
        return cities;
    }

    @Override
    public List<City> getAllActiveCities() {
        List<City> cities = cityRepository.findByStatus(Status.Active);
        logger.info("Number of cities found: {}", cities == null ? 0 : cities.size());
        return cities;
    }

    @Override
    public City getCityById(Long id) {
        City city = cityRepository.findOne(id);
        logger.info("City found for id {}: {}", id, city);
        return city;
    }

    @Override
    public List<City> getCitiesByName(String name) {
        List<City> cities = cityRepository.findByNameIgnoreCase(name);
        logger.info("Cities found with name {}: {}", name, cities);
        return cities;
    }

    @Override
    public City createCity(City city) {
        logger.info("creating new city: {}", city);

        // validate
        if (city == null) {
            logger.error("city is NULL");
            throw new RoamyValidationException("City info not provided");
        }
        if (!StringUtils.hasText(city.getName()) || !StringUtils.hasText(city.getCreatedBy())) {
            logger.error("name/createdBy not provided");
            throw new RoamyValidationException("name/createdBy not provided");
        }

        // set defaults [status:Inactive, createdOn: now, lastModifiedOn: now, lastModifiedBy: createdBy]
        if (city.getStatus() == null) {
            city.setStatus(Status.Inactive);
        }
        if (city.getCreatedOn() == null) {
            city.setCreatedOn(new Date());
        }
        if (city.getLastModifiedOn() == null) {
            city.setLastModifiedOn(new Date());
        }
        if (!StringUtils.hasText(city.getLastModifiedBy())) {
            city.setLastModifiedBy(city.getCreatedBy());
        }

        city = cityRepository.save(city);
        logger.info("created {}", city);

        return city;
    }

    @Override
    public City updateCity(City cityToUpdate) {
        logger.info("updating {}", cityToUpdate);

        // validate
        if (cityToUpdate == null) {
            logger.error("cityToUpdate is NULL");
            throw new RoamyValidationException("City data not provided");
        }
        if (cityToUpdate.getId() == null || !StringUtils.hasText(cityToUpdate.getName()) || !StringUtils.hasText(cityToUpdate.getLastModifiedBy())) {
            logger.error("id/name/lastModifiedBy not provided");
            throw new RoamyValidationException("id/name/lastModifiedBy not provided");
        }

        City city = getCityById(cityToUpdate.getId());
        if (city == null) {
            logger.error("city with id {} not found", cityToUpdate.getId());
            throw new RoamyValidationException("city with id {} not found");
        }

        logger.info("found object to update: {}", city);

        // update values
        if (StringUtils.hasText(cityToUpdate.getName())) {
            city.setName(cityToUpdate.getName());
        }
        if (cityToUpdate.getStatus() != null) {
            city.setStatus(cityToUpdate.getStatus());
        }

        cityToUpdate.setLastModifiedBy(cityToUpdate.getLastModifiedBy());

        if (cityToUpdate.getLastModifiedOn() == null) {
            city.setLastModifiedOn(new Date());
        } else {
            city.setLastModifiedOn(cityToUpdate.getLastModifiedOn());
        }

        City updatedCity = cityRepository.save(city);
        logger.info("updated city: {}", updatedCity);

        return updatedCity;
    }


}
