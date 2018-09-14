package ifood.score.listeners;

import ifood.score.order.Order;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class OrderListener {

    @JmsListener(destination = "${queue.order.checkout.name}", concurrency = "1-100",
            containerFactory = "jmsListenerFactory")
    public void orderCheckout(Order order) {
        System.out.println("Order Received:" + order);
    }

    @JmsListener(destination = "${queue.order.cancel.name}", concurrency = "1-50")
    public void orderCancel(String uuid) {
        System.out.println("Order cancel: " + uuid);
    }
}
