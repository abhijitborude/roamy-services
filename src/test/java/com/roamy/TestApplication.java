package com.roamy;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by Abhijit on 10/5/2015.
 */
@Profile("unit-test")
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.roamy")
@Configuration
public class TestApplication {
}
