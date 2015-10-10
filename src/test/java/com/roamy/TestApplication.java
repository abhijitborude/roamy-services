package com.roamy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Abhijit on 10/5/2015.
 */
@Profile("unit-test")
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.roamy")
@Configuration
public class TestApplication {
}
