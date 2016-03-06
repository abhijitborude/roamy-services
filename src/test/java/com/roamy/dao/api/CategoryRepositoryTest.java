package com.roamy.dao.api;

import com.roamy.TestApplication;
import com.roamy.domain.Category;
import com.roamy.domain.Status;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Abhijit on 10/8/2015.
 */
@ActiveProfiles("unit-test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@IntegrationTest
public class CategoryRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(CategoryRepositoryTest.class);

    private static final Long GET_OUT_OF_THE_CITY = 1L;
    private static final String CATEGORY_NAME_ADVENTURE = "Get out of the city";

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void findCategoryByIdTest() {
        Category category = categoryRepository.findOne(GET_OUT_OF_THE_CITY);
        logger.info("findOne by id {}: {}", GET_OUT_OF_THE_CITY, category);

        //Assert.assertNotNull("There should be a category with id: " + GET_OUT_OF_THE_CITY, category);
        //Assert.assertEquals("Category name should be " + CATEGORY_NAME_ADVENTURE, CATEGORY_NAME_ADVENTURE, category.getName());
    }

    @Test
    public void findAllCategoryTest() {
        Iterable<Category> cities = categoryRepository.findAll();
        logger.info("findAll: {}", cities);

        Assert.assertNotNull("There should be at least one category", cities);
    }

    @Test
    public void testFindByNameIgnoreCase() throws Exception {
        String categoryName = CATEGORY_NAME_ADVENTURE.toLowerCase();
        List<Category> cities = categoryRepository.findByNameIgnoreCase(categoryName);
        logger.info("findByNameIgnoreCase by {}: {}", categoryName, cities);

        Assert.assertNotNull("There should be at least one category with name: " + categoryName, cities);
        for (Category category : cities) {
            Assert.assertTrue("Category with name different from " + categoryName + " found", category.getName().toLowerCase().equals(categoryName.toLowerCase()));
        }
    }

    @Test
    public void testFindByStatus() throws Exception {
        List<Category> cities = categoryRepository.findByStatus(Status.Inactive);
        logger.info("findByStatus Inactive: {}", cities);

        Assert.assertNotNull("There should be at least one Inactive category", cities);
    }
}
