package ro.msg.learning.shop.utils;

import org.springframework.beans.factory.annotation.Value;
import ro.msg.learning.shop.strategy.*;

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
