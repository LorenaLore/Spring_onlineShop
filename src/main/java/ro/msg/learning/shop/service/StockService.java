package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.exception.LocationException;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.model.StockId;
import ro.msg.learning.shop.repository.StockRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StockService {

    private final StockRepository stockRepository;
    private final LocationService locationService;

    @Transactional
    public void updateStockQuantity(Product product, Location location, int quantity) {
        Stock stock = stockRepository.findById(new StockId(product, location)).orElseThrow(() ->
                new EntityNotFoundException("Could not find Stock for product" + product.getId() + ", "
                        + product.getName() + "and location: " + location.getId() + ", " + location.getName()));

        if (stock.getQuantity() == quantity) {
            stockRepository.delete(stock);
        } else {
            stock.setQuantity(stock.getQuantity() - quantity);
            stockRepository.save(stock);
        }
    }

    public List<Stock> exportStocks(Integer locationId) throws LocationException {
        return locationService.getById(locationId).getStockList();
    }
}