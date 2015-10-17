package com.roamy.service.impl;

import com.roamy.TestApplication;
import com.roamy.domain.Category;
import com.roamy.domain.Status;
import com.roamy.service.api.CategoryService;
import com.roamy.util.RoamyValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Abhijit on 10/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@IntegrationTest
public class CategoryServiceImplTest {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImplTest.class);

    private static final Long CATEGORY_ID_ADVENTURE = 1L;
    private static final String CATEGORY_NAME_ADVENTURE = "Adventure";

    @Autowired
    CategoryService categoryService;

    @Test
    public void testGetAllCategories() throws Exception {
        List<Category> categories = categoryService.getAllCategories();
        logger.info("found categories: {}", categories);

        assertNotNull("There should be at least one category", categories);
        assertNotEquals("There should be at least one category", 0, categories.size());
    }

    @Test
    public void testGetAllActiveCategories() throws Exception {
        List<Category> categories = categoryService.getAllActiveCategories();
        logger.info("found active categories: {}", categories);

        assertNotNull("There should be at least one active category", categories);
        assertNotEquals("There should be at least one active category", 0, categories.size());

        for (Category category : categories) {
            if (!Status.Active.equals(category.getStatus())) {
                fail("Inactive category found");
            }
        }
    }

    @Test
    public void testGetCategoryById() throws Exception {
        Category category = categoryService.getCategoryById(CATEGORY_ID_ADVENTURE);

        assertNotNull("There should be a category with id: " + CATEGORY_ID_ADVENTURE, category);
        assertEquals("Category name should be " + CATEGORY_NAME_ADVENTURE, CATEGORY_NAME_ADVENTURE, category.getName());
    }

    @Test
    public void testGetCategoriesByName() throws Exception {
        String categoryName = CATEGORY_NAME_ADVENTURE.toLowerCase();
        List<Category> categories = categoryService.getCategoriesByName(categoryName);

        assertNotNull("There should be at least one category with name: " + categoryName, categories);
        for (Category category : categories) {
            assertTrue("Category with name different from " + categoryName + " found", category.getName().toLowerCase().equals(categoryName.toLowerCase()));
        }
    }

    @Test
    public void testCreateCategory() throws Exception {
        Category category = new Category();
        category.setName("Test Category");
        category.setDescription("Test Category Description");
        category.setCreatedBy("test");

        Category newCategory = categoryService.createCategory(category);
        assertNotNull("Category could not be saved", newCategory);
        assertNotNull("id after save can not be NULL", newCategory.getId());
        assertEquals("name does not match", category.getName(), newCategory.getName());
        assertEquals("description does not match", category.getDescription(), newCategory.getDescription());
        assertEquals("createdBy does not match", category.getCreatedBy(), newCategory.getCreatedBy());
    }

    @Test
    public void testCreateCategoryNoName() throws Exception {
        Category category = new Category();
        category.setCreatedBy("test");

        try {
            Category newCategory = categoryService.createCategory(category);
            fail("Category without name should not be saved");
        } catch (RoamyValidationException e) {

        }
    }

    @Test
    public void testCreateCategoryNoCreatedBy() throws Exception {
        Category category = new Category();
        category.setName("Test Category");

        try {
            Category newCategory = categoryService.createCategory(category);
            fail("Category without createdBy should not be saved");
        } catch (RoamyValidationException e) {

        }
    }

    @Test
    public void testCreateCategoryNull() throws Exception {
        try {
            Category category = categoryService.createCategory(null);
            fail("Null category should not be saved");
        } catch (RoamyValidationException e) {

        }
    }

    @Test
    public void testUpdateCategory() throws Exception {
        // first create a newCategory
        Category category = new Category();
        category.setName("Test Category");
        category.setDescription("Test Category Description");
        category.setCreatedBy("test");

        Category newCategory = categoryService.createCategory(category);
        assertNotNull("Category could not be saved", newCategory);

        // create a new category with it's values
        category = new Category();
        category.setId(newCategory.getId());
        category.setName(newCategory.getName() + "-1");
        category.setDescription(newCategory.getDescription() + "-1");
        category.setLastModifiedBy("test");

        Category updatedCategory = categoryService.updateCategory(category);
        assertNotNull("Category could not be updated", updatedCategory);
        assertEquals("name was not updated", newCategory.getName() + "-1", updatedCategory.getName());
        assertEquals("name was not updated", newCategory.getDescription() + "-1", updatedCategory.getDescription());
        assertNotEquals("lastModifiedOn not updated", newCategory.getLastModifiedOn(), updatedCategory.getLastModifiedOn());
        assertEquals("createdBy should not change after the update", newCategory.getCreatedBy(), updatedCategory.getCreatedBy());
        assertEquals("createdOn should not change after the update", newCategory.getCreatedOn(), updatedCategory.getCreatedOn());
        assertEquals("status should not change after the update", newCategory.getStatus(), updatedCategory.getStatus());
    }

    @Test
    public void testUpdateCategoryNullName() throws Exception {
        // first create a newCategory
        Category category = new Category();
        category.setName("Test Category");
        category.setCreatedBy("test");

        Category newCategory = categoryService.createCategory(category);
        assertNotNull("Category could not be saved", newCategory);

        // create a new category with it's values
        category = new Category();
        category.setId(newCategory.getId());
        category.setName(null);
        category.setLastModifiedBy("test");

        try {
            Category updatedCategory = categoryService.updateCategory(category);
            fail("newCategory should not be updated with NULL name");
        } catch (RoamyValidationException e) {

        }
    }

    @Test
    public void testUpdateCategoryNullLastModifiedBy() throws Exception {
        // first create a newCategory
        Category category = new Category();
        category.setName("Test Category");
        category.setCreatedBy("test");

        Category newCategory = categoryService.createCategory(category);
        assertNotNull("Category could not be saved", newCategory);

        // create a new category with it's values
        category = new Category();
        category.setId(newCategory.getId());
        category.setName(newCategory.getName() + "-1");

        try {
            Category updatedCategory = categoryService.updateCategory(category);
            fail("newCategory should not be updated with NULL lastModifiedBy");
        } catch (RoamyValidationException e) {

        }
    }

    @Test
    public void testUpdateCategoryNullId() throws Exception {
        // first create a newCategory
        Category category = new Category();
        category.setName("Test Category");
        category.setCreatedBy("test");

        Category newCategory = categoryService.createCategory(category);
        assertNotNull("Category could not be saved", newCategory);

        // create a new category with it's values
        category = new Category();
        category.setName(newCategory.getName() + "-1");
        category.setLastModifiedBy("test");

        try {
            Category updatedCategory = categoryService.updateCategory(category);
            fail("newCategory should not be updated with NULL id");
        } catch (RoamyValidationException e) {

        }
    }

    @Test
    public void testUpdateCategoryNull() throws Exception {
        try {
            Category updateCategory = categoryService.updateCategory(null);
            fail("NULL category should not be updated");
        } catch (RoamyValidationException e) {

        }
    }
}