package ro.msg.learning.shop.repository;

import org.springframework.data.repository.CrudRepository;
import ro.msg.learning.shop.model.Customer;

public interface CustomerRepository  extends CrudRepository<Customer, Integer> {
}
