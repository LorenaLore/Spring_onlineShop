package ro.msg.learning.shop.strategy;

import ro.msg.learning.shop.exception.LocationException;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.ShippingDetail;

import java.util.List;

public class GreedyLocationStrategy implements LocationStrategy {

    @Override
    public List<ShippingDetail> fetchShippingDetailsForOrder(Order order) throws LocationException {
        throw new LocationException("could not find location for delivering order: " + order.toString());

    }
}
