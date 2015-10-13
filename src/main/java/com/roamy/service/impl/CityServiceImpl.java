package com.roamy.service.impl;

import com.roamy.dao.api.CityRepository;
import com.roamy.domain.City;
import com.roamy.domain.Status;
import com.roamy.service.api.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Abhijit on 10/11/2015.
 */
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<City> getAllActiveCities() {
        return cityRepository.findByStatus(Status.Active);
    }

    @Override
    public City getCityById(Long id) {
        return cityRepository.findOne(id);
    }

    @Override
    public City getCityByName(String name) {
        return null;
    }


}
