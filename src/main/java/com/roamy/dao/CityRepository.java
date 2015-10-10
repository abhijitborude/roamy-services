package com.roamy.dao;

import com.roamy.domain.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Abhijit on 10/8/2015.
 */
@Repository
public interface CityRepository extends CrudRepository<City, Long> {
}
