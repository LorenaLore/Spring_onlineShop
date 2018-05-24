package ro.msg.learning.shop.model;

import com.sun.istack.internal.NotNull;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class ProductCategory {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Integer id;

    @NotNull
    private String name;
    private String description;
}
