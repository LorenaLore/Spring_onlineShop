package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.ShippingDetail;
import ro.msg.learning.shop.repository.ShippingDetailRepository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ShippingDetailService {

    private final ShippingDetailRepository shippingDetailRepository;
    private final StockService stockService;

    public ShippingDetail createShippingDetail(Order order, Product product, Location location, int quantity) {
        stockService.updateStockQuantity(product, location, quantity);
        ShippingDetail shippingDetail = new ShippingDetail(order, location, product, quantity, LocalDateTime.now());
        return shippingDetailRepository.save(shippingDetail);
    }

    List<ShippingDetail> getShippingDetailsBetweenDates(LocalDateTime dateFrom, LocalDateTime dateTo) {
        return shippingDetailRepository.findShippingBetweenDates(dateFrom, dateTo);
    }
}
