package com.roamy.dao.api;

import com.roamy.domain.City;
import com.roamy.domain.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Abhijit on 10/8/2015.
 */
@Repository
public interface CityRepository extends CrudRepository<City, Long> {

    List<City> findByStatus(Status status);

    List<City> findByNameIgnoreCase(String name);

    List<City> findByNameIgnoreCaseLike(String name);
}
