package com.roamy.web.resource;

import com.roamy.dto.RestResponse;
import com.roamy.util.RestUtils;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Abhijit on 11/12/2015.
 */
public abstract class AbstractResource<T, ID extends Serializable> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractResource.class);

    protected abstract JpaRepository<T, ID> getJpaRepository();

    /**
     * Validates the incoming entity data received via the API before it is saved
     *
     * @param entity entity received via API
     */
    protected abstract void validateForCreate(T entity);

    /**
     * Adds additional information to the entity and enrich it before returning via the API
     *
     * @param entity entity to be returned via API
     */
    protected abstract void enrichForGet(T entity);

    /**
     * Adds additional required information to the entity received via API before it is saved
     *
     * @param entity entity received via API
     */
    protected abstract void enrichForCreate(T entity);

    /**
     * Adds hyperlinks to the entity being returned via API. e.g. Trip has links to TripReviews
     *
     * @param entity entity to be enriched with hyperlinks
     */
    protected abstract void addLinks(T entity);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "Get all entities with pagination", notes = "Fetches all entities with pagination enabled. " +
                            "Pagination can be controlled using two parameters- page and size. " +
                            "By default first page is displayed with 100 elements (page=0, size=100). " +
                            "Actual result is contained in the data field of the response.")
    public RestResponse findAll(@ApiParam(value = "Page number", defaultValue = "0", required = false)
                                    @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                @ApiParam(value = "Page size", defaultValue = "100", required = false)
                                    @RequestParam(value = "size", required = false, defaultValue = "100") int size) {

        RestResponse response = null;
        try {
            // load entities
            Page<T> entities = getJpaRepository().findAll(new PageRequest(page, size));
            List<T> content = entities.getContent();
            LOGGER.info("Number of entities of {} found: {}", this.getClass().getSimpleName(), content == null ? 0 : content.size());

            // enrich each entity
            if (content != null) {
                content.forEach(e -> enrichForGet(e));
            }

            // construct return result
            response = new RestResponse(content, HttpStatus.OK_200, null, RestUtils.getLinks(entities, size));

        } catch (Throwable t) {
            LOGGER.error("error in findAll of {}: ", this.getClass().getSimpleName(), t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }

}
