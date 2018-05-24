package ro.msg.learning.shop.model;

import com.sun.istack.internal.NotNull;
import lombok.Data;

import javax.persistence.*;

/**
 * POJO for address
 */
@Data
@Embeddable
public class Address {

    private String country;
    @NotNull
    private String city;
    private String county;
    @NotNull
    private String street;
}
