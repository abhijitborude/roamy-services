package com.roamy.web.resource;

import com.roamy.dao.api.TripInstanceRepository;
import com.roamy.domain.TripInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Abhijit on 11/15/2015.
 */
@RestController
@RequestMapping("/tripinstances")
public class TripInstanceResource extends IdentityResource<TripInstance, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripInstanceResource.class);

    @Autowired
    private TripInstanceRepository tripInstanceRepository;

    @Override
    protected JpaRepository<TripInstance, Long> getJpaRepository() {
        return tripInstanceRepository;
    }

    @Override
    protected void validate(TripInstance entity) {

    }

    @Override
    protected void enrich(TripInstance entity) {

    }

    @Override
    protected void addLinks(TripInstance entity) {

    }
}
