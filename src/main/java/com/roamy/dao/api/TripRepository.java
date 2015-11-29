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
    List<Trip> findByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetween(Status status, List<String> cityCodes, Status instancesStatus, Date startDate, Date endDate);

    List<Trip> findByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultAsc(Status status, List<String> cityCodes, Status instancesStatus, Date startDate, Date endDate);

    List<Trip> findByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultDesc(Status status, List<String> cityCodes, Status instancesStatus, Date startDate, Date endDate);

    List<Trip> findByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByDifficultyLevelAsc(Status status, List<String> cityCodes, Status instancesStatus, Date startDate, Date endDate);

    List<Trip> findByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByDifficultyLevelDesc(Status status, List<String> cityCodes, Status instancesStatus, Date startDate, Date endDate);

    // filter by status, start and end dates, category code
    List<Trip> findByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetween(Status status, List<String> cityCodes, List<String> categoryCodes, Status instancesStatus, Date startDate, Date endDate);

    List<Trip> findByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultAsc(Status status, List<String> cityCodes, List<String> categoryCodes, Status instancesStatus, Date startDate, Date endDate);

    List<Trip> findByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultDesc(Status status, List<String> cityCodes, List<String> categoryCodes, Status instancesStatus, Date startDate, Date endDate);

    List<Trip> findByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByDifficultyLevelAsc(Status status, List<String> cityCodes, List<String> categoryCodes, Status instancesStatus, Date startDate, Date endDate);

    List<Trip> findByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByDifficultyLevelDesc(Status status, List<String> cityCodes, List<String> categoryCodes, Status instancesStatus, Date startDate, Date endDate);

}
