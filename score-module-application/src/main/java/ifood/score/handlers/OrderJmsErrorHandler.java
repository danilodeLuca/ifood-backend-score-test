package ifood.score.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

@Service
public class OrderJmsErrorHandler implements ErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderJmsErrorHandler.class);

    @Override
    public void handleError(Throwable e) {
        LOGGER.error("Some error ocurried in JMS", e);
    }
}
