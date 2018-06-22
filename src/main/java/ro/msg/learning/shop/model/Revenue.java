package ro.msg.learning.shop.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class Revenue {

    @Id
    @Column(name = "revenue_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    private LocalDateTime date;

    private BigDecimal sum;

    public Revenue(Location location, LocalDateTime date, BigDecimal sum) {
        this.location = location;
        this.date = date;
        this.sum = sum;
    }
}
