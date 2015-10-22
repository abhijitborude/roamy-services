package com.roamy.dao.api;

import com.roamy.domain.Category;
import com.roamy.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Abhijit on 10/13/2015.
 */
@Repository
public interface CategoryRepository extends CitableRepository<Category, Long> {

}
