package ro.msg.learning.shop.converter;

import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.Stock;

public class StockToDTO {

    public static StockDTO toDTO(Stock stock) {
        Product stockProduct = stock.getId().getProduct();
        return new StockDTO(stockProduct.getId(), stockProduct.getName(), stockProduct.getDescription(),
                stockProduct.getPrice(), stockProduct.getWeight(), stock.getQuantity());
    }
}
