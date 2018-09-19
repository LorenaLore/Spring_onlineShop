package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.OrderDetailId;

import java.util.List;

public interface OrderDetailRepository  extends CrudRepository<OrderDetail, OrderDetailId> {

    @Query("SELECT od FROM OrderDetail od WHERE od.id.order.id = ?1")
    List<OrderDetail> findByOrderId(Integer orderId);
}