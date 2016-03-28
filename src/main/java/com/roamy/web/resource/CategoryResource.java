package com.roamy.web.resource;

import com.roamy.dao.api.CategoryRepository;
import com.roamy.dao.api.CitableRepository;
import com.roamy.domain.Category;
import com.roamy.domain.Status;
import com.roamy.dto.CategoryDto;
import com.roamy.dto.RestResponse;
import com.roamy.util.RestUtils;
import com.roamy.util.RoamyUtils;
import com.roamy.util.RoamyValidationException;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Abhijit on 11/12/2015.
 */
@RestController
@RequestMapping("/categories")
@Api(value = "category")
public class CategoryResource extends CitableResource<Category, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CitableResource.class);

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    protected CitableRepository<Category, Long> getCitableRepository() {
        return categoryRepository;
    }

    @Override
    protected void enrichForGet(Category entity) {

    }

    @Override
    protected void addLinks(Category entity) {

    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Create a Category", notes = "Creates a Category and returns the newly created entity." +
            "Actual result is contained in the data field of the response.")
    public RestResponse createCategory(@ApiParam(value = "Category to be created in the JSON format sent as payload of the POST operation.",
                                                required = true)
                                   @RequestBody CategoryDto categoryDto) {
        LOGGER.info("Saving: {}", categoryDto);

        RestResponse response = null;

        try {
            // validate
            if (!StringUtils.hasText(categoryDto.getCode())) {
                throw new RoamyValidationException("Category code not provided");
            }
            if (!StringUtils.hasText(categoryDto.getName())) {
                throw new RoamyValidationException("Category name not provided");
            }
            if (!StringUtils.hasText(categoryDto.getImageUrl())) {
                throw new RoamyValidationException("Category image url not provided");
            }

            // add missing information to the entity before it is saved
            Category category = new Category();
            category.setCode(categoryDto.getCode());
            category.setName(categoryDto.getName());
            category.setDescription(categoryDto.getDescription());
            category.setImageUrl(categoryDto.getImageUrl());
            category.setImageCaption(categoryDto.getImageCaption());
            category.setStatus(Status.Active);
            RoamyUtils.addAuditPropertiesForCreateEntity(category, "test");

            // save the entity
            category = categoryRepository.save(category);
            LOGGER.info("Category Saved: {}", category);

            response = new RestResponse(category, HttpStatus.OK_200);

        } catch (Throwable t) {
            LOGGER.error("error in createCategory: ", t);
            response = new RestResponse(null, HttpStatus.INTERNAL_SERVER_ERROR_500, RestUtils.getErrorMessages(t), null);
        }

        return response;
    }
}
