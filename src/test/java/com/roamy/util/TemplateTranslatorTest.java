package com.roamy.util;

import com.roamy.TestApplication;
import com.roamy.domain.PackageTrip;
import com.roamy.domain.Trip;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Abhijit on 2/7/2016.
 */
@ActiveProfiles("unit-test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@IntegrationTest
public class TemplateTranslatorTest {

    @Autowired
    private TemplateTranslator templateTranslator;

    @Test
    public void testTranslate() throws Exception {
        Trip trip = new PackageTrip();
        trip.setName("package trip");

        Map<String, Object> values = new HashMap<>();
        values.put("test", trip);

        templateTranslator.translate("this is a $test.name", values);
    }
}