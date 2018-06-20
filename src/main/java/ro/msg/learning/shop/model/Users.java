package ro.msg.learning.shop.model;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ro.msg.learning.shop.enums.UserType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> result = new ArrayList<>();
        result.add(new SimpleGrantedAuthority(UserType.CUSTOMER.name()));
        return result;

    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        // credentials never expire
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // we never lock accounts
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
