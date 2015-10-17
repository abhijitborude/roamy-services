package com.roamy.service.impl;

import com.roamy.dao.api.CategoryRepository;
import com.roamy.domain.Category;
import com.roamy.domain.Status;
import com.roamy.service.api.CategoryService;
import com.roamy.util.RoamyValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by Abhijit on 10/11/2015.
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        logger.info("Number of categories found: {}", categories == null ? 0 : categories.size());
        return categories;
    }

    @Override
    public List<Category> getAllActiveCategories() {
        List<Category> categories = categoryRepository.findByStatus(Status.Active);
        logger.info("Number of categories found: {}", categories == null ? 0 : categories.size());
        return categories;
    }

    @Override
    public Category getCategoryById(Long id) {
        Category category = categoryRepository.findOne(id);
        logger.info("Category found for id {}: {}", id, category);
        return category;
    }

    @Override
    public List<Category> getCategoriesByName(String name) {
        List<Category> categories = categoryRepository.findByNameIgnoreCase(name);
        logger.info("Categories found with name {}: {}", name, categories);
        return categories;
    }

    @Override
    public Category createCategory(Category category) {
        logger.info("creating new category: {}", category);

        // validate
        if (category == null) {
            logger.error("category is NULL");
            throw new RoamyValidationException("Category data not provided");
        }
        if (!StringUtils.hasText(category.getName()) || !StringUtils.hasText(category.getCreatedBy())) {
            logger.error("name/createdBy not provided");
            throw new RoamyValidationException("name/createdBy not provided");
        }

        // set defaults [status:Inactive, createdOn: now, lastModifiedOn: now, lastModifiedBy: createdBy]
        if (category.getStatus() == null) {
            category.setStatus(Status.Inactive);
        }
        if (category.getCreatedOn() == null) {
            category.setCreatedOn(new Date());
        }
        if (category.getLastModifiedOn() == null) {
            category.setLastModifiedOn(new Date());
        }
        if (!StringUtils.hasText(category.getLastModifiedBy())) {
            category.setLastModifiedBy(category.getCreatedBy());
        }

        category = categoryRepository.save(category);
        logger.info("created {}", category);

        return category;
    }

    @Override
    public Category updateCategory(Category categoryToUpdate) {
        logger.info("updating {}", categoryToUpdate);

        // validate
        if (categoryToUpdate == null) {
            logger.error("categoryToUpdate is NULL");
            throw new RoamyValidationException("Category data not provided");
        }
        if (categoryToUpdate.getId() == null || !StringUtils.hasText(categoryToUpdate.getName()) || !StringUtils.hasText(categoryToUpdate.getLastModifiedBy())) {
            logger.error("id/name/lastModifiedBy not provided");
            throw new RoamyValidationException("id/name/lastModifiedBy not provided");
        }

        Category category = getCategoryById(categoryToUpdate.getId());
        if (category == null) {
            logger.error("category with id {} not found", categoryToUpdate.getId());
            throw new RoamyValidationException("category with id {} not found");
        }

        logger.info("found object to update: {}", category);

        // update values
        if (StringUtils.hasText(categoryToUpdate.getName())) {
            category.setName(categoryToUpdate.getName());
        }
        if (StringUtils.hasText(categoryToUpdate.getDescription())) {
            category.setDescription(categoryToUpdate.getDescription());
        }
        if (categoryToUpdate.getStatus() != null) {
            category.setStatus(categoryToUpdate.getStatus());
        }

        categoryToUpdate.setLastModifiedBy(categoryToUpdate.getLastModifiedBy());

        if (categoryToUpdate.getLastModifiedOn() == null) {
            category.setLastModifiedOn(new Date());
        } else {
            category.setLastModifiedOn(categoryToUpdate.getLastModifiedOn());
        }

        Category updatedCategory = categoryRepository.save(category);
        logger.info("updated category: {}", updatedCategory);

        return updatedCategory;
    }


}
