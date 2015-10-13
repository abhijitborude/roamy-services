package com.roamy.service.api;

import com.roamy.domain.City;
import com.roamy.dto.CityDto;

import java.util.List;

/**
 * Created by Abhijit on 10/11/2015.
 */
public interface CityService {

    List<City> getAllActiveCities();

    City getCityById(Long id);

    List<City> getCitiesByName(String name);

    City createCity(CityDto dto);

    City updateCity(CityDto dto);
}
