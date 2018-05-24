package ro.msg.learning.shop.repository;

import org.springframework.data.repository.CrudRepository;
import ro.msg.learning.shop.model.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {
}
