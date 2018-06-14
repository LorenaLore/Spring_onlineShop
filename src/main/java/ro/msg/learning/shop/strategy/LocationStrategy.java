package ro.msg.learning.shop.strategy;

import ro.msg.learning.shop.exception.LocationException;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.ShippingDetail;

import java.util.List;

public interface LocationStrategy {

    List<ShippingDetail> fetchShippingDetailsForOrder(Order order) throws LocationException;
}
