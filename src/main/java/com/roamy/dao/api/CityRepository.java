package com.roamy.dao.api;

import com.roamy.domain.City;
import org.springframework.stereotype.Repository;

/**
 * Created by Abhijit on 10/8/2015.
 */
@Repository
public interface CityRepository extends CitableRepository<City, Long> {

}
