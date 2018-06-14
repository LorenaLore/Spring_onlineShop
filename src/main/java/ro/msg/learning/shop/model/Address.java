package ro.msg.learning.shop.model;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

/**
 * POJO for address
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {

    private String country;
    @NotNull
    private String city;
    private String county;
    @NotNull
    private String street;
}
