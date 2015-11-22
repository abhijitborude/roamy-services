package com.roamy.web.resource;

import com.roamy.domain.AbstractEntity;
import com.roamy.dto.RestResponse;
import com.roamy.util.RestUtils;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;

/**
 * Created by Abhijit on 11/15/2015.
 */
public abstract class IdentityResource<T extends AbstractEntity, ID extends Serializable> extends AbstractResource<T, ID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(IdentityResource.class);

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RestResponse findById(@PathVariable ID id) {

        RestResponse response = null;

        try {
            // find object
            T entity = getJpaRepository().findOne(id);
            LOGGER.info("entity by id({}): {}", id, entity);

            // Enrich the entity e.g. set additional properties
            enrich(entity);

            // Add hyperlinks to the related entities e.g. TripReview link in Trip resource
            addLinks(entity);

            // return response
            response = new RestResponse(entity, HttpStatus.OK_200, null, null);

        } catch (Throwable t) {
            LOGGER.error("error in findOne: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }

}
