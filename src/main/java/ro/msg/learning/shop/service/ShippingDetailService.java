package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.ShippingDetail;
import ro.msg.learning.shop.repository.ShippingDetailRepository;

@Service
public class ShippingDetailService {

    private ShippingDetailRepository shippingDetailRepository;
    private StockService stockService;

    @Autowired
    public ShippingDetailService(ShippingDetailRepository shippingDetailRepository, StockService stockService) {
        this.shippingDetailRepository = shippingDetailRepository;
        this.stockService = stockService;
    }

    public ShippingDetail createShippingDetail(Order order, Product product, Location location, int quantity) {
        stockService.updateStockQuantity(product, location, quantity);
        ShippingDetail shippingDetail = new ShippingDetail(order, location, product, quantity);
        return shippingDetailRepository.save(shippingDetail);
    }
}
