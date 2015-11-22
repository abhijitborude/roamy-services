package com.roamy.web.resource;

import com.roamy.dto.RestResponse;
import com.roamy.util.RestUtils;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestBody;
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

    protected abstract void validate(T entity);

    protected abstract void enrich(T entity);

    protected abstract void addLinks(T entity);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public RestResponse findAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page, @RequestParam(value = "size", required = false, defaultValue = "100") int size) {

        RestResponse response = null;
        try {
            Page<T> entities = getJpaRepository().findAll(new PageRequest(page, size));
            List<T> content = entities.getContent();
            LOGGER.info("Number of entities found: {}", content == null ? 0 : content.size());

            response = new RestResponse(content, HttpStatus.OK_200, null, RestUtils.getLinks(entities, size));

        } catch (Throwable t) {
            LOGGER.error("error in findAll: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public RestResponse createOne(@RequestBody T entity) {
        LOGGER.info("Saving: {}", entity);

        RestResponse response = null;

        try {
            validate(entity);

            T savedEntity = getJpaRepository().save(entity);
            LOGGER.info("Entity Saved: {}", savedEntity);

            response = new RestResponse(savedEntity, HttpStatus.OK_200);

        } catch (Throwable t) {
            LOGGER.error("error in save: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }
}
