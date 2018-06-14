package ro.msg.learning.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@Entity
public class OrderDetail {

    @Id
    @Embedded
    @Column(unique = true)
    private OrderDetailId id;

    private int quantity;
}
