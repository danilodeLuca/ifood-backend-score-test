package ifood.score.listeners;

import ifood.score.order.Order;
import ifood.score.services.OrderRelevanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import java.util.UUID;

@Component
public class OrderListener {

    @Autowired
    private OrderRelevanceService orderRelevanceService;

    @JmsListener(destination = "${queue.order.checkout.name}", concurrency = "1-100",
            containerFactory = "jmsListenerFactory")
    public void orderCheckout(Order order) {
        System.out.println("Order Received:" + order);
        orderRelevanceService.checkoutOrderAndCalculateRelevance(order);
    }

    @JmsListener(destination = "${queue.order.cancel.name}", concurrency = "1-50")
    public void orderCancel(String orderId) {
        System.out.println("#################Order cancel: " + orderId);
        orderRelevanceService.cancelOrder(orderId.replaceAll("\"", ""));
    }
}
