package ro.msg.learning.shop.repository;

import org.springframework.data.repository.CrudRepository;
import ro.msg.learning.shop.model.ProductCategory;

public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Integer> {
}
