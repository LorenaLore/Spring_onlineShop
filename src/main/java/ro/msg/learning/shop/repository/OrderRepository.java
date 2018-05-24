package ro.msg.learning.shop.repository;

import org.springframework.data.repository.CrudRepository;
import ro.msg.learning.shop.model.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {
}
