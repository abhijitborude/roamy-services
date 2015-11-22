package com.roamy.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Abhijit on 7/1/2015.
 */
@Entity
@Table(name = "CATEGORY", schema = "ROAMY")
public class Category extends CitableEntity {

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
