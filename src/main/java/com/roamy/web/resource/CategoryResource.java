package com.roamy.web.resource;

import com.roamy.dao.api.CategoryRepository;
import com.roamy.dao.api.CitableRepository;
import com.roamy.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Abhijit on 11/12/2015.
 */
@RestController
@RequestMapping("/categories")
public class CategoryResource extends CitableResource<Category, Long> {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    protected CitableRepository<Category, Long> getCitableRepository() {
        return categoryRepository;
    }

    @Override
    protected void validate(Category entity) {

    }

    @Override
    protected void enrich(Category entity) {

    }

    @Override
    protected void addLinks(Category entity) {

    }
}
