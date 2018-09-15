package ifood.score.services;

import ifood.score.builders.RelevanceOrderBuilder;
import ifood.score.order.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderRelevanceService {

    public void calculateOrderRelevance(Order order) {
        RelevanceOrderBuilder builder = RelevanceOrderBuilder.from(order).build();
    }
}
