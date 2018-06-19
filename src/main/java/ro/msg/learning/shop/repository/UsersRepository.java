package ro.msg.learning.shop.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ro.msg.learning.shop.model.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    Users findOneByUsername(String username);
}
