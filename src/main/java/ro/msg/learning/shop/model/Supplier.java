package ro.msg.learning.shop.model;

import com.sun.istack.internal.NotNull;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Supplier {

    @Id
    @GeneratedValue
    @Column(name = "supplier_id")
    private Integer id;

    @NotNull
    @Column(unique = true)
    private String name;
}
