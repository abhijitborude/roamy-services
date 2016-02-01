package com.roamy.dao.api;

import com.roamy.domain.TripOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Abhijit on 1/31/2016.
 */
@Repository
public interface TripOptionRepository extends JpaRepository<TripOption, Long> {
}
