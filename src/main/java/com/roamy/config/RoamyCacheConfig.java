package com.roamy.config;

import com.roamy.domain.City;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;

/**
 * Created by Abhijit on 5/7/2016.
 */
@Configuration
@ConditionalOnWebApplication
public class RoamyCacheConfig {

    @Bean
    public CacheManager jCacheManager() {
        CacheManager cacheManager = Caching.getCachingProvider().getCacheManager();
        cacheManager.createCache("allCities", new MutableConfiguration<String, City>()
                .setStoreByValue(false)
                .setManagementEnabled(true)
                .setStatisticsEnabled(true)
                .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_HOUR)));

        cacheManager.createCache("categoriesForCity", new MutableConfiguration<String, City>()
                .setStoreByValue(false)
                .setManagementEnabled(true)
                .setStatisticsEnabled(true)
                .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_HOUR)));
        return cacheManager;
    }
}
