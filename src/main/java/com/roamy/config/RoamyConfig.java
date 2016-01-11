package com.roamy.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Abhijit on 12/15/2015.
 */
@Configuration
public class RoamyConfig {

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "abhijitab",
                "api_key", "875519512844545",
                "api_secret", "e-ZQpulZQ2lKBhv72u4D1zgzGV4"
        ));

        return cloudinary;
    }

    @Bean
    public ConfigProperties configProperties() {
        return new ConfigProperties();
    }
}
