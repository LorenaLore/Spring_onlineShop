package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.LocationException;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.LocationRepository;

import java.util.List;


@Service
public class LocationService {

    private LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location getSingleLocationForOrder(Order order) {
        for (Location location : locationRepository.findAll()) {
            boolean locationOk = true;
            for (OrderDetail od : order.getOrderDetailList()) {
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
            if (od.getId().getProduct() == stock.getId().getProduct()) {
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
