package ro.msg.learning.shop.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
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

    private LocalDateTime shippingDate;

    public ShippingDetail(Order order, Location location, Product product, int quantity, LocalDateTime shippingDate) {
        this.order = order;
        this.location = location;
        this.product = product;
        this.quantity = quantity;
        this.shippingDate = shippingDate;
    }
}