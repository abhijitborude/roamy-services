package com.roamy.dao.api;

import com.roamy.domain.Status;
import com.roamy.domain.TargetCity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Abhijit on 11/21/2015.
 */
public interface TargetCityRepository extends JpaRepository<TargetCity, Long> {

    List<TargetCity> findByCityCodeAndTripStatus(String code, Status active);
}
