package com.roamy.dao.api;

import com.roamy.domain.TripReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Abhijit on 11/15/2015.
 */
@Repository
public interface TripReviewRepository extends JpaRepository<TripReview, Long> {

    List<TripReview> findByTripCode(String code);
}
