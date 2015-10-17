package com.roamy.service.api;

import com.roamy.domain.City;

import java.util.List;

/**
 * Created by Abhijit on 10/11/2015.
 */
public interface CityService {

    List<City> getAllCities();

    List<City> getAllActiveCities();

    City getCityById(Long id);

    List<City> getCitiesByName(String name);

    City createCity(City city);

    City updateCity(City city);
}
