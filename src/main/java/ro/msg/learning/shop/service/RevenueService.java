package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Revenue;
import ro.msg.learning.shop.model.ShippingDetail;
import ro.msg.learning.shop.repository.RevenueRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RevenueService {

    private RevenueRepository revenueRepository;
    private ShippingDetailService shippingDetailService;

    @Autowired
    public RevenueService(RevenueRepository revenueRepository, ShippingDetailService shippingDetailService) {
        this.revenueRepository = revenueRepository;
        this.shippingDetailService = shippingDetailService;
    }

    /**
     * this method is called from CollectLocationRevenueScheduler, daily, at 00.00.00
     * it calculates and saves the revenue for each location for the last 24 hours
     */
    public void fetchDailyRevenue() {
        LocalDateTime dateFrom = LocalDateTime.now().minusDays(1);
        LocalDateTime dateTo = LocalDateTime.now();
        List<ShippingDetail> shippingDetailList = shippingDetailService.getShippingDetailsBetweenDates(dateFrom,
                dateTo);

        Map<Location, BigDecimal> shippingDetailMap = new HashMap<>();
        for (ShippingDetail shippingDetail : shippingDetailList) {
            if (!shippingDetailMap.containsKey(shippingDetail.getLocation())) {
                shippingDetailMap.put(shippingDetail.getLocation(), BigDecimal.ZERO);
            }
            BigDecimal oldValue = shippingDetailMap.get(shippingDetail.getLocation());
            BigDecimal newValue = oldValue.add(
                    shippingDetail.getProduct().getPrice().multiply(BigDecimal.valueOf(shippingDetail.getQuantity())));
            shippingDetailMap.put(shippingDetail.getLocation(), newValue);
        }

        List<Revenue> revenueList = new ArrayList<>();
        for (Map.Entry<Location, BigDecimal> entry : shippingDetailMap.entrySet()) {
            revenueList.add(new Revenue(entry.getKey(), LocalDateTime.now(), entry.getValue()));
        }

        revenueRepository.saveAll(revenueList);
    }
}
