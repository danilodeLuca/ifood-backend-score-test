package ifood.score.order;

import ifood.score.handlers.OrderJmsHandler;
import ifood.score.mock.generator.order.OrderPicker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.IntStream;

import static ifood.score.mock.generator.RandomishPicker._int;

@Service
@Profile({"dev"})
public class OrderCheckoutMock {

    @Autowired
    private OrderJmsHandler orderJmsHandler;

    private ConcurrentLinkedQueue<UUID> cancellantionQueue = new ConcurrentLinkedQueue<>();

    private static OrderPicker picker = new OrderPicker();

    @Value("${queue.order.checkout.name}")
    private String checkoutQueue;

    @Value("${queue.order.cancel.name}")
    private String cancelQueue;

    @Scheduled(fixedRateString = "${queue.order.checkout.fixedRate}")
    public void checkoutFakeOrder() {
        IntStream.rangeClosed(1, _int(2, 12)).forEach(t -> {
            Order order = picker.pick();
            if (_int(0, 20) % 20 == 0) {
                cancellantionQueue.add(order.getUuid());
            }
            orderJmsHandler.sendMessage(checkoutQueue, order);
        });
    }

    @Scheduled(fixedRateString = "${queue.order.cancel.fixedRate}")
    public void cancelFakeOrder() {
        IntStream.range(1, _int(2, cancellantionQueue.size() > 2 ? cancellantionQueue.size() : 2)).forEach(t -> {
            UUID orderUuid = cancellantionQueue.poll();
            if (orderUuid != null) {
                orderJmsHandler.sendMessage(cancelQueue, orderUuid);
            }
        });
    }

}
