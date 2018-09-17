package ifood.score.services;

import ifood.score.entities.OrderJmsError;
import ifood.score.handlers.OrderJmsHandler;
import ifood.score.repositories.OrderJmsErrorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderJmsService {

    @Autowired
    private OrderJmsErrorRepository orderJmsErrorRepository;
    @Autowired
    private OrderJmsHandler orderJmsHandler;

    public void recoverErros() {
        orderJmsErrorRepository.findByCriticalFalse().forEach(this::retry);
    }

    private void retry(OrderJmsError orderJmsError) {
        try {
            boolean failedProcessed = orderJmsHandler.sendMessageFromError(orderJmsError.getQueueName(), orderJmsError.getObject());
            if (failedProcessed)
                orderJmsErrorRepository.delete(orderJmsError);

        } catch (ClassNotFoundException e) {
            orderJmsError.setCritical(Boolean.TRUE);
            orderJmsErrorRepository.save(orderJmsError);
        }
    }

    public OrderJmsError addError(String queueName, Object messageSent) {
        OrderJmsError orderJmsError = new OrderJmsError(queueName, messageSent);
        return orderJmsErrorRepository.save(orderJmsError);
    }
}
