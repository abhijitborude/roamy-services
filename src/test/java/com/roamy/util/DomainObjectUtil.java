package com.roamy.util;

import com.roamy.domain.Category;
import com.roamy.domain.City;
import com.roamy.domain.Status;

import java.util.Date;

/**
 * Created by Abhijit on 4/10/2016.
 */
public class DomainObjectUtil {

    public static City createCity(String code, String name) {
        City city = new City();
        city.setCode(code);
        city.setName(name);
        city.setDescription("Test Description");
        city.setStatus(Status.Active);
        city.setCreatedBy("test");
        city.setCreatedOn(new Date());
        city.setLastModifiedBy("test");
        city.setLastModifiedOn(new Date());
        return city;
    }

    public static Category createCategory(String code, String name) {
        Category category = new Category();
        category.setCode(code);
        category.setName(name);
        category.setDescription("Test Description");
        category.setImageCaption("Test Caption");
        category.setImageUrl("http://test");
        category.setStatus(Status.Active);
        category.setCreatedBy("test");
        category.setLastModifiedBy("test");
        return category;
    }
}
