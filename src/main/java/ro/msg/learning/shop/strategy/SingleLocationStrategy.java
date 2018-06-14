package ro.msg.learning.shop.strategy;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.LocationException;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.ShippingDetail;
import ro.msg.learning.shop.service.LocationService;
import ro.msg.learning.shop.service.ShippingDetailService;

import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
public class SingleLocationStrategy implements LocationStrategy {

    LocationService locationService;
    ShippingDetailService shippingDetailService;

    @Autowired
    public SingleLocationStrategy(LocationService locationService, ShippingDetailService shippingDetailService) {
        this.locationService = locationService;
        this.shippingDetailService = shippingDetailService;
    }

    @Override
    public List<ShippingDetail> fetchShippingDetailsForOrder(Order order) throws LocationException {
        Location location = locationService.getSingleLocationForOrder(order);
        if (location == null) {
            throw new LocationException("could not find location for delivering order: " + order.getId());
        }
        List<ShippingDetail> shippingDetails = new ArrayList<>();
        for (OrderDetail orderDetail : order.getOrderDetailList()) {
            shippingDetails.add(
                    shippingDetailService.createShippingDetail(order, orderDetail.getId().getProduct(), location,
                            orderDetail.getQuantity()));
        }
        return shippingDetails;
    }
}
