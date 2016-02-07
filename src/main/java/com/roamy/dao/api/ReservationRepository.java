package com.roamy.dao.api;

import com.roamy.domain.Reservation;
import com.roamy.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Abhijit on 12/13/2015.
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findTop50ByUserIdAndStartDateLessThanOrderByStartDateDesc(Long userId, Date date);

    List<Reservation> findByUserIdAndStatusAndStartDateGreaterThanEqualOrderByStartDateAsc(Long userId, Status status, Date date);
}
