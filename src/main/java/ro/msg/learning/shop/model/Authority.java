package ro.msg.learning.shop.model;

import lombok.Data;
import ro.msg.learning.shop.enums.UserType;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authority_id")
    private Long id;

    private UserType name;

    @ManyToMany(mappedBy = "authorities")
    private Set<Users> users;
}
