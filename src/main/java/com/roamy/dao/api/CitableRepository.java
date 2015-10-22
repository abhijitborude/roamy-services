package com.roamy.dao.api;

import com.roamy.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Abhijit on 10/22/2015.
 */
public interface CitableRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    List<T> findByCode(String code);

    List<T> findByCodeAndStatus(String code, Status status);

    List<T> findByName(String name);

}
