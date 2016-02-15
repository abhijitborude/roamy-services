package com.roamy.web.resource;

import com.roamy.dao.api.CategoryRepository;
import com.roamy.dao.api.CitableRepository;
import com.roamy.domain.Category;
import com.roamy.domain.Status;
import com.roamy.util.RoamyUtils;
import com.roamy.util.RoamyValidationException;
import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by Abhijit on 11/12/2015.
 */
@RestController
@RequestMapping("/categories")
@Api(value = "category")
public class CategoryResource extends CitableResource<Category, Long> {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    protected CitableRepository<Category, Long> getCitableRepository() {
        return categoryRepository;
    }

    @Override
    protected void validateForCreate(Category entity) {
        if (!StringUtils.hasText(entity.getCode())) {
            throw new RoamyValidationException("Category code not provided");
        }
        if (!StringUtils.hasText(entity.getName())) {
            throw new RoamyValidationException("Category name not provided");
        }
    }

    @Override
    protected void enrichForGet(Category entity) {

    }

    @Override
    protected void enrichForCreate(Category entity) {
        if (entity.getStatus() == null) {
            entity.setStatus(Status.Active);
        }
        RoamyUtils.addAuditPropertiesForCreateEntity(entity, "test");
    }

    @Override
    protected void afterEntityCreated(Category entity) {

    }

    @Override
    protected void addLinks(Category entity) {

    }
}
