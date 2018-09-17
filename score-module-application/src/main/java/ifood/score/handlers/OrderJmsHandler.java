package ifood.score.handlers;

import ifood.score.services.OrderJmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderJmsHandler {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private OrderJmsService orderJmsService;

    private Boolean worksWell = true;

    private LocalDateTime retry = LocalDateTime.now();

    public void sendMessage(String destination, Object message) {
        if (worksWell || runned5MinutesAgo()) {
            retry = LocalDateTime.now();
            try {
                jmsTemplate.convertAndSend(destination, message);
                if (!worksWell) {
                    orderJmsService.recoverErros();
                }

                worksWell = true;
            } catch (JmsException ex) {
                orderJmsService.addError(destination, message);
                worksWell = false;
            }
        } else {
            orderJmsService.addError(destination, message);
        }
    }

    public boolean sendMessageFromError(String destination, Object message) {
        if (worksWell) {
            try {
                jmsTemplate.convertAndSend(destination, message);
                worksWell = true;
                return true;
            } catch (JmsException ex) {
                worksWell = false;
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean runned5MinutesAgo() {
        return retry.isBefore(LocalDateTime.now().minusMinutes(5));
    }

}
