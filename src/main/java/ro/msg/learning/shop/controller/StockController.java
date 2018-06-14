package ro.msg.learning.shop.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.converter.StockToDTO;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.exception.LocationException;
import ro.msg.learning.shop.service.StockService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/stock")
public class StockController {

    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/export/{locationId}", produces = "text/csv")
    public List<StockDTO> exportStockForLocation(@PathVariable Integer locationId) throws LocationException {
        return stockService.exportStocks(locationId).stream()
                .map(StockToDTO::toDTO)
                .collect(Collectors.toList());
    }
}
