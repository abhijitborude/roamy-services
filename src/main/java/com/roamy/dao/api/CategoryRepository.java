package com.roamy.dao.api;

import com.roamy.domain.Category;
import com.roamy.domain.City;
import com.roamy.domain.Status;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Abhijit on 10/13/2015.
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

    List<Category> findByStatus(Status status);

    List<Category> findByNameIgnoreCase(String name);

}
