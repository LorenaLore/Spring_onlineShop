package ro.msg.learning.shop.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Stock {

    @Id
    @Embedded
    @Column(unique = true)
    private StockId id;

    private int quantity;
}
