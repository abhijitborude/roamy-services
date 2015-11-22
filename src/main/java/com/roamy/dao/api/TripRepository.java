package com.roamy.dao.api;

import com.roamy.domain.Status;
import com.roamy.domain.Trip;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Abhijit on 11/15/2015.
 */
@Repository
public interface TripRepository extends CitableRepository<Trip, Long> {

    List<Trip> findByStatusAndInstancesStatusAndInstancesDateBetween(Status status, Status instancesStatus, Date startDate, Date endDate);

    List<Trip> findByStatusAndInstancesStatusAndInstancesDateBetweenOrderByDefaultPricePerAdultAsc(Status status, Status instancesStatus, Date startDate, Date endDate);

    List<Trip> findByStatusAndInstancesStatusAndInstancesDateBetweenOrderByDefaultPricePerAdultDesc(Status status, Status instancesStatus, Date startDate, Date endDate);

    List<Trip> findByStatusAndInstancesStatusAndInstancesDateBetweenOrderByDifficultyLevelAsc(Status status, Status instancesStatus, Date startDate, Date endDate);

    List<Trip> findByStatusAndInstancesStatusAndInstancesDateBetweenOrderByDifficultyLevelDesc(Status status, Status instancesStatus, Date startDate, Date endDate);
}
