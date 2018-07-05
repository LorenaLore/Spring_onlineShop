package ro.msg.learning.shop.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "SHIPPING_DETAIL")
public class ShippingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipping_id", columnDefinition = "BIGINT(20,0)")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    private LocalDateTime shipping_date;

    public ShippingDetail(Order order, Location location, Product product, int quantity, LocalDateTime shipping_date) {
        this.order = order;
        this.location = location;
        this.product = product;
        this.quantity = quantity;
        this.shipping_date = shipping_date;
    }
}