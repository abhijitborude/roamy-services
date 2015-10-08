package in.roamy.domain;

import in.roamy.util.DbConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by Abhijit on 7/1/2015.
 */
@Entity
@Table(name = "CATEGORY", schema = "ROAMY")
public class Category extends AbstractEntity{

    @NotNull
    @Column(name = "NAME", length = DbConstants.SHORT_TEXT)
    private String name;

    @Column(name = "DESCRIPTION", length = DbConstants.MEDIUM_TEXT)
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                '}';
    }
}
