package com.roamy.dao.api;

import com.roamy.domain.FavoriteTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Abhijit on 12/12/2015.
 */
@Repository
public interface FavoriteTripRepository extends JpaRepository<FavoriteTrip, Long> {

    List<FavoriteTrip> findByUserPhoneNumber(String phoneNumber);

    FavoriteTrip findByUserIdAndTripCode(Long userId, String tripCode);
}
