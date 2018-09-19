package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@Service
public class RevenueService {

    private final RevenueRepository revenueRepository;
    private final ShippingDetailService shippingDetailService;

    /**
     * this method is called from CollectLocationRevenueScheduler, daily, at 00.00.00
     * it calculates and saves the revenue for each location for the last 24 hours
     */
    public void fetchDailyRevenue() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dateFrom = now.minusDays(1);
        List<ShippingDetail> shippingDetailList = shippingDetailService.getShippingDetailsBetweenDates(dateFrom,
                now);

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
            revenueList.add(new Revenue(entry.getKey(), now, entry.getValue()));
        }

        revenueRepository.saveAll(revenueList);
    }
}
