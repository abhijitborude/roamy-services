package com.roamy.dao.api;

import com.roamy.domain.Category;
import com.roamy.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Abhijit on 10/22/2015.
 */
@NoRepositoryBean
public interface CitableRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    List<T> findByCode(String code);

    List<T> findByCodeAndStatus(String code, Status status);

    List<Category> findByStatus(Status status);

    List<T> findByName(String name);

}
