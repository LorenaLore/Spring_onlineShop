package ro.msg.learning.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.converter.StockToDTO;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.exception.LocationException;
import ro.msg.learning.shop.service.StockService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/stock")
public class StockController {

    private final StockService stockService;

    @GetMapping(value = "/export/{locationId}", produces = "text/csv")
    public List<StockDTO> exportStockForLocation(@PathVariable Integer locationId) throws LocationException {
        return stockService.exportStocks(locationId).stream()
                .map(StockToDTO::toDTO)
                .collect(Collectors.toList());
    }
}
