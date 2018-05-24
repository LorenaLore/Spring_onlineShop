package ro.msg.learning.shop.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class OrderDetail {

    @Id
    @Embedded
    @Column(unique = true)
    private OrderDetailId id;

    private int quantity;
}
