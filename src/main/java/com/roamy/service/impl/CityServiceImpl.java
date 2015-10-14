package com.roamy.service.impl;

import com.roamy.dao.api.CityRepository;
import com.roamy.domain.City;
import com.roamy.domain.Status;
import com.roamy.dto.CityDto;
import com.roamy.service.api.CityService;
import com.roamy.util.DtoUtil;
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
        logger.info("creating new city: {}", dto);

        // validate
        if (dto == null) {
            logger.error("dto is NULL");
            throw new RoamyValidationException("City data not provided");
        }
        if (!StringUtils.hasText(dto.getName()) || !StringUtils.hasText(dto.getCreatedBy())) {
            logger.error("name/createdBy not provided");
            throw new RoamyValidationException("name/createdBy not provided");
        }

        // set defaults [status:Inactive, createdOn: now, lastModifiedOn: now, lastModifiedBy: createdBy]
        if (!StringUtils.hasText(dto.getStatus())) {
            dto.setStatus(Status.Inactive.name());
        }
        if (dto.getCreatedOn() == null) {
            dto.setCreatedOn(new Date());
        }
        if (dto.getLastModifiedOn() == null) {
            dto.setLastModifiedOn(new Date());
        }
        if (!StringUtils.hasText(dto.getLastModifiedBy())) {
            dto.setLastModifiedBy(dto.getCreatedBy());
        }

        // convert to domain object and save
        City city = DtoUtil.convertToCity(dto);

        city = cityRepository.save(city);
        logger.info("created {}", city);

        return city;
    }

    @Override
    public City updateCity(CityDto dto) {
        logger.info("updating {}", dto);

        // validate
        if (dto == null) {
            logger.error("dto is NULL");
            throw new RoamyValidationException("City data not provided");
        }
        if (dto.getId() == null || !StringUtils.hasText(dto.getName()) || !StringUtils.hasText(dto.getLastModifiedBy())) {
            logger.error("id/name/lastModifiedBy not provided");
            throw new RoamyValidationException("id/name/lastModifiedBy not provided");
        }

        City city = getCityById(dto.getId());
        if (city == null) {
            logger.error("city with id {} not found", dto.getId());
            throw new RoamyValidationException("city with id {} not found");
        }

        logger.info("found object to update: {}", city);

        // update values
        if (StringUtils.hasText(dto.getName())) {
            city.setName(dto.getName());
        }
        if (StringUtils.hasText(dto.getStatus()) && Status.valueOf(dto.getStatus()) != null) {
            city.setStatus(Status.valueOf(dto.getStatus()));
        }

        dto.setLastModifiedBy(dto.getLastModifiedBy());

        if (dto.getLastModifiedOn() == null) {
            city.setLastModifiedOn(new Date());
        } else {
            city.setLastModifiedOn(dto.getLastModifiedOn());
        }

        City updatedCity = cityRepository.save(city);
        logger.info("updated city: {}", updatedCity);

        return updatedCity;
    }


}
