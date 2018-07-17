package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
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
public class LocationService {

    private LocationRepository locationRepository;
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository, OrderDetailRepository orderDetailRepository) {
        this.locationRepository = locationRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

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
        try {
            return locationRepository.findById(locationId).get();
        } catch (NullPointerException npe) {
            throw new LocationException("Location with given id could no be found. Id: " + locationId);
        }
    }

}
