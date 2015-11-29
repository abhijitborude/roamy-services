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

    // filter by status, start and end dates
    List<Trip> findByStatusAndInstancesStatusAndInstancesDateBetween(Status status, Status instancesStatus, Date startDate, Date endDate);

    List<Trip> findByStatusAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultAsc(Status status, Status instancesStatus, Date startDate, Date endDate);

    List<Trip> findByStatusAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultDesc(Status status, Status instancesStatus, Date startDate, Date endDate);

    List<Trip> findByStatusAndInstancesStatusAndInstancesDateBetweenOrderByDifficultyLevelAsc(Status status, Status instancesStatus, Date startDate, Date endDate);

    List<Trip> findByStatusAndInstancesStatusAndInstancesDateBetweenOrderByDifficultyLevelDesc(Status status, Status instancesStatus, Date startDate, Date endDate);

    // filter by status, start and end dates, category code
    List<Trip> findByStatusAndCategoriesCodeAndInstancesStatusAndInstancesDateBetween(Status status, String categoryCode, Status instancesStatus, Date startDate, Date endDate);

    List<Trip> findByStatusAndCategoriesCodeAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultAsc(Status status, String categoryCode, Status instancesStatus, Date startDate, Date endDate);

    List<Trip> findByStatusAndCategoriesCodeAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultDesc(Status status, String categoryCode, Status instancesStatus, Date startDate, Date endDate);

    List<Trip> findByStatusAndCategoriesCodeAndInstancesStatusAndInstancesDateBetweenOrderByDifficultyLevelAsc(Status status, String categoryCode, Status instancesStatus, Date startDate, Date endDate);

    List<Trip> findByStatusAndCategoriesCodeAndInstancesStatusAndInstancesDateBetweenOrderByDifficultyLevelDesc(Status status, String categoryCode, Status instancesStatus, Date startDate, Date endDate);

}
