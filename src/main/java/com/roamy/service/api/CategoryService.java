package com.roamy.service.api;

import com.roamy.domain.Category;

import java.util.List;

/**
 * Created by Abhijit on 10/13/2015.
 */
public interface CategoryService {

    List<Category> getAllCategories();

    List<Category> getAllActiveCategories();

    Category getCategoryById(Long id);

    List<Category> getCategoriesByName(String name);

    Category createCategory(Category category);

    Category updateCategory(Category category);

}
