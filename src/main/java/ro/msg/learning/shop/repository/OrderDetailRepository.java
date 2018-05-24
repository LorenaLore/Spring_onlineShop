package ro.msg.learning.shop.repository;

import org.springframework.data.repository.CrudRepository;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.OrderDetailId;

public interface OrderDetailRepository  extends CrudRepository<OrderDetail, OrderDetailId> {
}