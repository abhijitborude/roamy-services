package com.roamy.service.impl;

import com.roamy.dao.api.CityRepository;
import com.roamy.domain.City;
import com.roamy.domain.Status;
import com.roamy.dto.CityDto;
import com.roamy.service.api.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Abhijit on 10/11/2015.
 */
@Service
public class CityServiceImpl implements CityService {

    private Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);

    @Autowired
    private CityRepository cityRepository;

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
    public City createCity(CityDto dto) {

        return null;
    }

    @Override
    public City updateCity(String name) {
        return null;
    }


}
