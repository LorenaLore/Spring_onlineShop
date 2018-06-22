package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ro.msg.learning.shop.model.ShippingDetail;

import java.time.LocalDateTime;
import java.util.List;

public interface ShippingDetailRepository extends CrudRepository<ShippingDetail, Integer>{

    @Query("SELECT sp FROM ShippingDetail sp WHERE sp.shipping_date BETWEEN ?1 AND ?2")
    List<ShippingDetail> findShippingBetweenDates(LocalDateTime dateFrom, LocalDateTime dateTo);

}
