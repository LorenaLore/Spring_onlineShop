package ro.msg.learning.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class StockDTO {

    private Integer productId;
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private BigDecimal productWeight;
    private int quantity;
}
