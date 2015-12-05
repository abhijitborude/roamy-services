package com.roamy.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.roamy.config.CustomDateSerializer;
import com.roamy.util.DbConstants;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abhijit on 7/1/2015.
 */
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    protected Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = DbConstants.SHORT_TEXT)
    protected Status status;

    @NotNull
    @CreatedDate
    @Column(name = "CREATED_ON", updatable = false)
    @JsonSerialize(using = CustomDateSerializer.class)
    protected Date createdOn = new Date();

    @NotNull
    @CreatedBy
    @Column(name = "CREATED_BY", updatable = false, length = DbConstants.MEDIUM_TEXT)
    protected String createdBy = "";

    @NotNull
    @Version
    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_ON")
    @JsonSerialize(using = CustomDateSerializer.class)
    protected Date lastModifiedOn = new Date();

    @NotNull
    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY", length = DbConstants.MEDIUM_TEXT)
    protected String lastModifiedBy = "";

    @Transient
    protected Map<String, String> _links = new HashMap<String, String>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(Date lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Map<String, String> get_links() {
        return _links;
    }

    public void set_links(Map<String, String> links) {
        this._links = links;
    }
}
