package ro.msg.learning.shop.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.service.RevenueService;

@Component
public class CollectLocationRevenueScheduler {

    private RevenueService revenueService;

    public CollectLocationRevenueScheduler(RevenueService revenueService) {
        this.revenueService = revenueService;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void fetchDailyRevenues(){
        revenueService.fetchDailyRevenue();
    }
}
