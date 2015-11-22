package com.roamy.dao.api;

import com.roamy.domain.Category;
import org.springframework.stereotype.Repository;

/**
 * Created by Abhijit on 10/13/2015.
 */
@Repository
public interface CategoryRepository extends CitableRepository<Category, Long> {

}
