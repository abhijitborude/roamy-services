package com.roamy.dao.api;

import com.roamy.domain.City;
import com.roamy.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Abhijit on 10/8/2015.
 */
@Repository
public interface CityRepository extends CitableRepository<City, Long> {

}
