package com.roamy;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by Abhijit on 10/5/2015.
 */
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.roamy")
@Configuration
public class TestApplication {
}
