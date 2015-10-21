package com.roamy.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Abhijit on 7/1/2015.
 */
@Entity
@Table(name = "CITY", schema = "ROAMY")
public class City extends CitableEntity {

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
