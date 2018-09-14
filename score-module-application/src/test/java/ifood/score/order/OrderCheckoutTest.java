
package ifood.score.order;

import ifood.score.mock.generator.order.OrderPicker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrderCheckoutTest {

    @Value("${queue.order.checkout.name}")
    private String checkoutQueue;

    @Value("${queue.order.cancel.name}")
    private String cancelQueue;

    private static OrderPicker picker = new OrderPicker();

    @Autowired
    private JmsTemplate jmsTemplate;

    private Collection<UUID> cancelantionListIds = new ArrayList<>();

    @Test
    public void testQueues() throws InterruptedException {
        Order order = picker.pick();
        jmsTemplate.convertAndSend(cancelQueue, order);

        Order order2 = picker.pick();
        jmsTemplate.convertAndSend(checkoutQueue, order2);

        boolean hasMessages = true;
        do {
            Integer allMessages = messagesToProcess(cancelQueue) + messagesToProcess(checkoutQueue);
            if (allMessages <= 0) {
                hasMessages = false;
            } else {
                System.out.println("woops some messages were not completed");
            }
            Thread.sleep(2 * 1000L);
        } while (hasMessages);
    }

    private Integer messagesToProcess(String queueName) {
        return jmsTemplate.browse(queueName,
                (s, queueProcessor) -> Collections.list(queueProcessor.getEnumeration()).size());
    }

}
