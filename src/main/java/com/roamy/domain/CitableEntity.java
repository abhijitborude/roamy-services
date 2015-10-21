package com.roamy.domain;

import com.roamy.util.DbConstants;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * Created by Abhijit on 10/20/2015.
 */
@MappedSuperclass
public abstract class CitableEntity extends AbstractEntity {

    @NotNull
    @Column(name = "CODE", length = DbConstants.SHORT_TEXT, unique = true)
    protected String code;

    @NotNull
    @Column(name = "NAME", length = DbConstants.SHORT_TEXT)
    protected String name;

    @Column(name = "DESCRIPTION", length = DbConstants.MEDIUM_TEXT)
    protected String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdditionalDescription() {
        return description;
    }

    public void setAdditionalDescription(String description) {
        this.description = description;
    }
}
