package ro.msg.learning.shop.model;

import com.sun.istack.internal.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Integer id;

    @NotNull
    private String name;

    private String description;

    private BigDecimal price;

    private double weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private ProductCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

}
