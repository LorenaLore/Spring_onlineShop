package ro.msg.learning.shop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.utils.CsvMappingUtility;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class TestCsvMappingUtility {

    CsvMappingUtility<StockDTO> csvMappingUtility = new CsvMappingUtility<>();

    @Test
    public void testToCsv() throws IOException {
        List<StockDTO> stockList = new ArrayList<>();
        stockList.add(
                new StockDTO(1, "Beer - Upper Canada Lager", "Pineapple - Regular", new BigDecimal(4.00), 0.8, 4));
        stockList.add(new StockDTO(4, "Veal - Bones", "Clementine", new BigDecimal(6.00), 1, 20));
        OutputStream os = new FileOutputStream("stock.csv");
        csvMappingUtility.toCsv(StockDTO.class, stockList, os);
    }

    @Test
    public void testFromCsv() throws IOException {
        InputStream csvInput = new FileInputStream(new File("stock.csv"));
        List<StockDTO> stockList = csvMappingUtility.fromCsv(StockDTO.class, csvInput);
        assertTrue(stockList.size() == 2);
    }
}
