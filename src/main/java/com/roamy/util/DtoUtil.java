package com.roamy.util;

import com.roamy.domain.Category;
import com.roamy.domain.City;
import com.roamy.domain.Status;
import com.roamy.dto.CategoryDto;
import com.roamy.dto.CityDto;

/**
 * Created by Abhijit on 10/12/2015.
 */
public class DtoUtil {

    public static City convertToCity(CityDto dto) {
        City city = new City();
        city.setName(dto.getName());
        city.setStatus(Status.valueOf(dto.getStatus()));
        city.setCreatedOn(dto.getCreatedOn());
        city.setCreatedBy(dto.getCreatedBy());
        city.setLastModifiedOn(dto.getLastModifiedOn());
        city.setLastModifiedBy(dto.getLastModifiedBy());
        return city;
    }

    public static CityDto convertToDto(City city) {
        CityDto dto = new CityDto();
        dto.setId(city.getId());
        dto.setName(city.getName());
        dto.setStatus(city.getStatus().name());
        dto.setCreatedOn(city.getCreatedOn());
        dto.setCreatedBy(city.getCreatedBy());
        dto.setLastModifiedOn(city.getLastModifiedOn());
        dto.setLastModifiedBy(city.getLastModifiedBy());
        return dto;
    }

    public static Category convertToCategory(CategoryDto dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setStatus(Status.valueOf(dto.getStatus()));
        category.setCreatedOn(dto.getCreatedOn());
        category.setCreatedBy(dto.getCreatedBy());
        category.setLastModifiedOn(dto.getLastModifiedOn());
        category.setLastModifiedBy(dto.getLastModifiedBy());
        return category;
    }

    public static CategoryDto convertToDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setStatus(category.getStatus().name());
        dto.setCreatedOn(category.getCreatedOn());
        dto.setCreatedBy(category.getCreatedBy());
        dto.setLastModifiedOn(category.getLastModifiedOn());
        dto.setLastModifiedBy(category.getLastModifiedBy());
        return dto;
    }
}
