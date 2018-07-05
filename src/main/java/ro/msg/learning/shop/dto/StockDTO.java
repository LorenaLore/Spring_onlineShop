package ro.msg.learning.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class StockDTO {

    private Integer product_id;
    private String product_name;
    private String product_description;
    private BigDecimal product_price;
    private BigDecimal product_weight;
    private int quantity;
}
