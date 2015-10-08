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
@Table(name = "CITY", schema = "ROAMY")
public class City extends AbstractEntity {

    @NotNull
    @Column(name = "NAME", length = DbConstants.SHORT_TEXT)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
