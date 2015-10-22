package com.roamy.dao.api;

import com.roamy.domain.Category;
import com.roamy.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Abhijit on 10/13/2015.
 */
public interface CategoryRepository extends CitableRepository<Category, Long> {

    List<Category> findByStatus(Status status);

    List<Category> findByNameIgnoreCase(String name);

}
