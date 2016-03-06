package com.roamy.domain;

import com.roamy.util.DbConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by Abhijit on 2/2/2016.
 */
@Entity
@Table(name = "EMAIL_TEMPLATE")
public class EmailTemplate extends CitableEntity {

    @NotNull
    @Column(name = "SUBJECT_TEMPLATE", length = DbConstants.LONG_TEXT)
    private String subjectTemplate;

    @NotNull
    @Column(name = "TEMPLATE", length = DbConstants.ULTRA_LONG_TEXT)
    private String template;

    @NotNull
    @Column(name = "EMAIL_TYPE", length = DbConstants.SHORT_TEXT)
    private String emailType;

    public String getSubjectTemplate() {
        return subjectTemplate;
    }

    public void setSubjectTemplate(String subjectTemplate) {
        this.subjectTemplate = subjectTemplate;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    @Override
    public String toString() {
        return "EmailTemplate{" +
                "code='" + code + '\'' +
                ", emailType='" + emailType + '\'' +
                '}';
    }
}
