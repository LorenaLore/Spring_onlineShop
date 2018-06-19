package ro.msg.learning.shop.utils;

import org.springframework.beans.factory.annotation.Value;
import ro.msg.learning.shop.enums.AvailableLocationStrategy;
import ro.msg.learning.shop.strategy.GreedyLocationStrategy;
import ro.msg.learning.shop.strategy.LocationStrategy;
import ro.msg.learning.shop.strategy.MostAbundantLocationStrategy;
import ro.msg.learning.shop.strategy.SingleLocationStrategy;

public class CurrentLocationStrategy {

    @Value("${current.location.strategy}")
    private static AvailableLocationStrategy currentLocationStrategy;

    public static LocationStrategy getCurrentLocationStrategy() {
        switch (currentLocationStrategy) {
            case SINGLE:
                return new SingleLocationStrategy();
            case MOST_ABUNDANT:
                return new MostAbundantLocationStrategy();
            case GREEDY:
                return new GreedyLocationStrategy();
            default:
                return new SingleLocationStrategy();
        }
    }

}
