package com.roamy.dao.api;

import com.roamy.domain.CitableEntity;
import com.roamy.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Abhijit on 10/22/2015.
 */
@NoRepositoryBean
public interface CitableRepository<T extends CitableEntity, ID extends Serializable> extends JpaRepository<T, ID> {

    T findByCode(String code);

    T findByCodeAndStatus(String code, Status status);

    List<T> findByStatus(Status status);

    List<T> findByName(String name);

    List<T> findByNameIgnoreCase(String name);

    List<T> findByNameIgnoreCaseLike(String name);

}
