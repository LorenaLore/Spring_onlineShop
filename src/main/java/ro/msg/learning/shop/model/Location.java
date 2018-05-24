package ro.msg.learning.shop.model;

import com.sun.istack.internal.NotNull;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Location {

    @Id
    @GeneratedValue
    @Column(name = "location_id")
    private Integer id;

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    @Embedded
    private Address address;
}
