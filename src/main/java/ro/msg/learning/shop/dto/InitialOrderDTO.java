package ro.msg.learning.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ro.msg.learning.shop.model.Address;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class InitialOrderDTO {
    private LocalDateTime date;
    private Address address;
    private List<ProductDTO> products;
}
