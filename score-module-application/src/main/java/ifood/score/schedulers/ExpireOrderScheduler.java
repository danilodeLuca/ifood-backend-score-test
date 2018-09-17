package ifood.score.schedulers;

import ifood.score.repositories.RelevanceOrderRepository;
import ifood.score.services.OrderRelevanceService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ExpireOrderScheduler {

    @Autowired
    private OrderRelevanceService orderRelevanceService;

    @Value("${order.relevance.expire-days}")
    private Integer daysToExpire;

    @Scheduled(cron = "0 */5 * * * ?")
    public void expireOrder() {
        Date dateToExpire = DateTime.now().minusDays(daysToExpire).toDate();
        orderRelevanceService.expireOrdersBeforeDate(dateToExpire);
    }
}
