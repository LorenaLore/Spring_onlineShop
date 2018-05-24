package ro.msg.learning.shop.repository;

import org.springframework.data.repository.CrudRepository;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.model.StockId;

public interface StockRepository extends CrudRepository<Stock, StockId> {
}
