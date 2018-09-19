package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.LocationException;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.OrderDetailRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final OrderDetailRepository orderDetailRepository;

    public Location getSingleLocationForOrder(Order order) {
        for (Location location : locationRepository.findAll()) {
            boolean locationOk = true;
            for (OrderDetail od : orderDetailRepository.findByOrderId(order.getId())) {
                if (!productFoundInStock(od, location.getStockList())) {
                    locationOk = false;
                    break;
                }
            }
            if (locationOk) {
                return location;
            }
        }
        return null;

    }

    private boolean productFoundInStock(OrderDetail od, List<Stock> stockList) {
        for (Stock stock : stockList) {
            if (od.getId().getProduct().getId().equals(stock.getId().getProduct().getId())) {
                return od.getQuantity() <= stock.getQuantity();
            }
        }
        return false;
    }

    public Location getById(Integer locationId) throws LocationException {
        return locationRepository.findById(locationId).orElseThrow(() ->
                new LocationException("Location with given id could no be found. Id: " + locationId));
    }

}
