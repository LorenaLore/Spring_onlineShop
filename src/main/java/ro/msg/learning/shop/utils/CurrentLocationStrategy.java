package ro.msg.learning.shop.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.enums.AvailableLocationStrategy;
import ro.msg.learning.shop.strategy.GreedyLocationStrategy;
import ro.msg.learning.shop.strategy.LocationStrategy;
import ro.msg.learning.shop.strategy.MostAbundantLocationStrategy;
import ro.msg.learning.shop.strategy.SingleLocationStrategy;

@Component
public class CurrentLocationStrategy {

    private SingleLocationStrategy singleLocationStrategy;
    private MostAbundantLocationStrategy mostAbundantLocationStrategy;
    private GreedyLocationStrategy greedyLocationStrategy;

    @Autowired
    public CurrentLocationStrategy(SingleLocationStrategy singleLocationStrategy,
                                   MostAbundantLocationStrategy mostAbundantLocationStrategy,
                                   GreedyLocationStrategy greedyLocationStrategy) {
        this.singleLocationStrategy = singleLocationStrategy;
        this.mostAbundantLocationStrategy = mostAbundantLocationStrategy;
        this.greedyLocationStrategy = greedyLocationStrategy;

    }

    @Value("${current.location.strategy}")
    private AvailableLocationStrategy currentLocationStrategy;

    public LocationStrategy getCurrentLocationStrategy() {
        switch (currentLocationStrategy) {
            case SINGLE:
                return singleLocationStrategy;
            case MOST_ABUNDANT:
                return mostAbundantLocationStrategy;
            case GREEDY:
                return greedyLocationStrategy;
            default:
                return singleLocationStrategy;
        }
    }

}
