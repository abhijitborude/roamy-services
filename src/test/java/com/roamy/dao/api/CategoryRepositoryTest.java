package com.roamy.dao.api;

import com.roamy.TestApplication;
import com.roamy.domain.Category;
import com.roamy.domain.Status;
import com.roamy.util.DomainObjectUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Abhijit on 10/8/2015.
 */
@ActiveProfiles("unit-test")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Rollback
@SpringApplicationConfiguration(classes = TestApplication.class)
public class CategoryRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryRepositoryTest.class);

    private static final String TEST_NAME = "Test Category";
    private static final String TEST_CODE = "TEST_CATEGORY";

    private Long id;

    @Autowired
    CategoryRepository categoryRepository;

    @Before
    public void setUp() {
        Category category = categoryRepository.save(DomainObjectUtil.createCategory(TEST_CODE, TEST_NAME));
        id = category.getId();
        LOGGER.info("Category saved with id: " + id);
    }

    @After
    public void tearDown() {
        //categoryRepository.deleteAll();
    }

    @Test
    public void findCategoryByIdTest() {
        Category category = categoryRepository.findOne(id);
        LOGGER.info("findOne by id {}: {}", id, category);

        Assert.assertNotNull("There should be a category with id: " + id, category);
        Assert.assertEquals("Category name should be " + TEST_NAME, TEST_NAME, category.getName());
    }

    @Test
    public void findAllCategoryTest() {
        Iterable<Category> cities = categoryRepository.findAll();
        LOGGER.info("findAll: {}", cities);

        Assert.assertNotNull("There should be at least one category", cities);
    }

    @Test
    public void testFindByNameIgnoreCase() throws Exception {
        String categoryName = TEST_NAME.toLowerCase();
        List<Category> categories = categoryRepository.findByNameIgnoreCase(categoryName);
        LOGGER.info("findByNameIgnoreCase by {}: {}", categoryName, categories);

        Assert.assertNotNull("There should be at least one category with name: " + categoryName, categories);
        for (Category category : categories) {
            Assert.assertTrue("Category with name different from " + categoryName + " found", category.getName().toLowerCase().equals(categoryName.toLowerCase()));
        }
    }

    @Test
    public void testFindByStatus() throws Exception {
        List<Category> categories = categoryRepository.findByStatus(Status.Active);
        LOGGER.info("findByStatus Inactive: {}", categories);

        Assert.assertNotNull("There should be at least one active category", categories);
    }
}
