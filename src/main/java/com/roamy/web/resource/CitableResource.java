package com.roamy.web.resource;

import com.roamy.dao.api.CitableRepository;
import com.roamy.domain.CitableEntity;
import com.roamy.dto.RestResponse;
import com.roamy.util.RestUtils;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;

/**
 * Created by Abhijit on 11/12/2015.
 */
public abstract class CitableResource<T extends CitableEntity, ID extends Serializable> extends AbstractResource<T, ID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CitableResource.class);

    protected abstract CitableRepository<T, ID> getCitableRepository();

    protected JpaRepository<T, ID> getJpaRepository() {
        return getCitableRepository();
    }

    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
    public RestResponse findByCode(@PathVariable String code) {

        RestResponse response = null;

        try {
            // find object
            T entity = getCitableRepository().findByCode(code);
            LOGGER.info("entity by code({}): {}", code, entity);

            // Enrich the entity e.g. set additional properties
            enrich(entity);

            // Add hyperlinks to the related entities e.g. TripReview link in Trip resource
            addLinks(entity);

            // return response
            response = new RestResponse(entity, HttpStatus.OK_200, null, null);

        } catch (Throwable t) {
            LOGGER.error("error in findAll: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }

}
