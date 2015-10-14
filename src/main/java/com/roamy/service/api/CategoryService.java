package com.roamy.service.api;

import com.roamy.domain.Category;
import com.roamy.dto.CategoryDto;

import java.util.List;

/**
 * Created by Abhijit on 10/13/2015.
 */
public interface CategoryService {

    List<Category> getAllActiveCategories();

    Category getCategoryById(Long id);

    List<Category> getCategoriesByName(String name);

    Category createCategory(CategoryDto dto);

    Category updateCategory(CategoryDto dto);

}
