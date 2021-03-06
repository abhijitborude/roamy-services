package com.roamy.dao.api;

import com.roamy.domain.Status;
import com.roamy.domain.TripInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Abhijit on 11/15/2015.
 */
@Repository
public interface TripInstanceRepository extends JpaRepository<TripInstance, Long> {

    List<TripInstance> findTop60ByTripCodeAndStatus(String code, Status status);

    List<TripInstance> findByTripCodeAndDateAndStatus(String code, Date date, Status status);
}
