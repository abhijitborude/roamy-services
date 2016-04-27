package com.roamy.config;

import com.hazelcast.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Abhijit on 4/25/16.
 */
@Configuration
public class HazelcastConfiguration {

    @Bean
    public Config config() {
        return new Config();
    }
}
