package ro.msg.learning.shop.model;

import com.sun.istack.internal.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Integer id;

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    @Embedded
    private Address address;

    @OneToMany(mappedBy = "id.location", cascade = CascadeType.REMOVE)
    private List<Stock> stockList;
}
