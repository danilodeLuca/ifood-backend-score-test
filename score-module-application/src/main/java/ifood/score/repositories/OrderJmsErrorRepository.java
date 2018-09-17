package ifood.score.repositories;

import ifood.score.entities.OrderJmsError;
import ifood.score.entities.RelevanceOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface OrderJmsErrorRepository extends MongoRepository<OrderJmsError, String> {

    List<OrderJmsError> findByCriticalFalse();
}
