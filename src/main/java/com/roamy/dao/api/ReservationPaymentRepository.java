package com.roamy.dao.api;

import com.roamy.domain.ReservationPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Abhijit on 1/11/2016.
 */
@Repository
public interface ReservationPaymentRepository extends JpaRepository<ReservationPayment, Long> {
}
