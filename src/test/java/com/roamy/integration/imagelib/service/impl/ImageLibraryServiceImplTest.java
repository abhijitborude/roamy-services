package com.roamy.integration.imagelib.service.impl;

import com.roamy.TestApplication;
import com.roamy.integration.imagelib.service.api.ImageLibraryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Abhijit on 12/28/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@IntegrationTest
public class ImageLibraryServiceImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageLibraryServiceImplTest.class);

    @Autowired
    private ImageLibraryService imageLibraryService;

    @Test
    public void testUploadImage() throws Exception {
//        File file = new File("C:\\Users\\Abhijit\\Pictures\\Screenshots\\Screenshot.png");
//
//        ImageLibraryIdentifier identifier = imageLibraryService.uploadImage(Files.readAllBytes(file.toPath()));
//        LOGGER.info(identifier.toString());
//        Assert.assertNotNull("identifier can not be null", identifier);
    }
}