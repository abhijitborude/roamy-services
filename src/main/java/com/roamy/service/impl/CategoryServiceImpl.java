package com.roamy.service.impl;

import com.roamy.dao.api.CategoryRepository;
import com.roamy.domain.Category;
import com.roamy.domain.Status;
import com.roamy.dto.CategoryDto;
import com.roamy.service.api.CategoryService;
import com.roamy.service.api.CategoryService;
import com.roamy.util.DtoUtil;
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
    public Category createCategory(CategoryDto dto) {
        logger.info("creating new category: {}", dto);

        // validate
        if (dto == null) {
            logger.error("dto is NULL");
            throw new RoamyValidationException("Category data not provided");
        }
        if (!StringUtils.hasText(dto.getName()) || !StringUtils.hasText(dto.getCreatedBy())) {
            logger.error("name/createdBy not provided");
            throw new RoamyValidationException("name/createdBy not provided");
        }

        // set defaults [status:Inactive, createdOn: now, lastModifiedOn: now, lastModifiedBy: createdBy]
        if (!StringUtils.hasText(dto.getStatus())) {
            dto.setStatus(Status.Inactive.name());
        }
        if (dto.getCreatedOn() == null) {
            dto.setCreatedOn(new Date());
        }
        if (dto.getLastModifiedOn() == null) {
            dto.setLastModifiedOn(new Date());
        }
        if (!StringUtils.hasText(dto.getLastModifiedBy())) {
            dto.setLastModifiedBy(dto.getCreatedBy());
        }

        // convert to domain object and save
        Category category = DtoUtil.convertToCategory(dto);

        category = categoryRepository.save(category);
        logger.info("created {}", category);

        return category;
    }

    @Override
    public Category updateCategory(CategoryDto dto) {
        logger.info("updating {}", dto);

        // validate
        if (dto == null) {
            logger.error("dto is NULL");
            throw new RoamyValidationException("Category data not provided");
        }
        if (dto.getId() == null || !StringUtils.hasText(dto.getName()) || !StringUtils.hasText(dto.getLastModifiedBy())) {
            logger.error("id/name/lastModifiedBy not provided");
            throw new RoamyValidationException("id/name/lastModifiedBy not provided");
        }

        Category category = getCategoryById(dto.getId());
        if (category == null) {
            logger.error("category with id {} not found", dto.getId());
            throw new RoamyValidationException("category with id {} not found");
        }

        logger.info("found object to update: {}", category);

        // update values
        if (StringUtils.hasText(dto.getName())) {
            category.setName(dto.getName());
        }
        if (StringUtils.hasText(dto.getDescription())) {
            category.setDescription(dto.getDescription());
        }
        if (StringUtils.hasText(dto.getStatus()) && Status.valueOf(dto.getStatus()) != null) {
            category.setStatus(Status.valueOf(dto.getStatus()));
        }

        dto.setLastModifiedBy(dto.getLastModifiedBy());

        if (dto.getLastModifiedOn() == null) {
            category.setLastModifiedOn(new Date());
        } else {
            category.setLastModifiedOn(dto.getLastModifiedOn());
        }

        Category updatedCategory = categoryRepository.save(category);
        logger.info("updated category: {}", updatedCategory);

        return updatedCategory;
    }


}
