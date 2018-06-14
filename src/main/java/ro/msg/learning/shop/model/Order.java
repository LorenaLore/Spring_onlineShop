package ro.msg.learning.shop.model;


import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(name="ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer id;

    @OneToMany(mappedBy = "order")
    private List<ShippingDetail> shippedFrom;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @NotNull
    @Embedded
    private Address address;

    private LocalDateTime date;


    @OneToMany(mappedBy = "id.order", cascade = CascadeType.REMOVE)
    private List<OrderDetail> orderDetailList;

    public Order(Customer customer, Address address, LocalDateTime date) {
        this.customer = customer;
        this.address = address;
        this.date = date;
    }
}
