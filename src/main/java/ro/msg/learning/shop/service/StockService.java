package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.LocationException;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.model.StockId;
import ro.msg.learning.shop.repository.StockRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    private StockRepository stockRepository;
    private LocationService locationService;

    @Autowired
    public StockService(StockRepository stockRepository, LocationService locationService) {
        this.stockRepository = stockRepository;
        this.locationService = locationService;
    }

    public Stock updateStockQuantity(Product product, Location location, int quantity) {
        Optional<Stock> stockOptional = stockRepository.findById(new StockId(product, location));
        if (!stockOptional.isPresent()) {
            throw new EntityNotFoundException(
                    "Could not find Stock for product" + product.getId() + ", " + product.getName() + "and location: "
                            + location.getId() + ", " + location.getName());
        }
        Stock stock = stockOptional.get();
        if (stock.getQuantity() == quantity) {
            stockRepository.delete(stock);
            return null;
        } else {
            stock.setQuantity(stock.getQuantity() - quantity);
            return stockRepository.save(stock);
        }
    }

    public List<Stock> exportStocks(Integer locationId) throws LocationException {
        return locationService.getById(locationId).getStockList();
    }
}