package ifood.score.repositories;

import ifood.score.entities.MenuItemScore;
import ifood.score.entities.RelevanceOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RelevanceOrderRepository extends MongoRepository<RelevanceOrder, UUID> {

}
