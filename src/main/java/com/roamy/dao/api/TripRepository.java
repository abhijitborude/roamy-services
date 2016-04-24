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
    List<Trip> findDistinctByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetween(Status status, List<String> cityCodes, Status instancesStatus, Date startDate, Date endDate);

    List<Trip> findDistinctByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultAsc(Status status, List<String> cityCodes, Status instancesStatus, Date startDate, Date endDate);

    List<Trip> findDistinctByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultDesc(Status status, List<String> cityCodes, Status instancesStatus, Date startDate, Date endDate);

    List<Trip> findDistinctByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByThrillMeterAsc(Status status, List<String> cityCodes, Status instancesStatus, Date startDate, Date endDate);

    List<Trip> findDistinctByStatusAndTargetCitiesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByThrillMeterDesc(Status status, List<String> cityCodes, Status instancesStatus, Date startDate, Date endDate);

    // filter by status, start and end dates, category code
    List<Trip> findDistinctByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetween(Status status, List<String> cityCodes, List<String> categoryCodes, Status instancesStatus, Date startDate, Date endDate);

    List<Trip> findDistinctByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultAsc(Status status, List<String> cityCodes, List<String> categoryCodes, Status instancesStatus, Date startDate, Date endDate);

    List<Trip> findDistinctByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByPricePerAdultDesc(Status status, List<String> cityCodes, List<String> categoryCodes, Status instancesStatus, Date startDate, Date endDate);

    List<Trip> findDistinctByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByThrillMeterAsc(Status status, List<String> cityCodes, List<String> categoryCodes, Status instancesStatus, Date startDate, Date endDate);

    List<Trip> findDistinctByStatusAndTargetCitiesCodeInAndCategoriesCodeInAndInstancesStatusAndInstancesDateBetweenOrderByThrillMeterDesc(Status status, List<String> cityCodes, List<String> categoryCodes, Status instancesStatus, Date startDate, Date endDate);

}
