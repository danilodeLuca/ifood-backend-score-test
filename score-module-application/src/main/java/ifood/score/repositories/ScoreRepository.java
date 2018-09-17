package ifood.score.repositories;

import ifood.score.entities.MenuItemScore;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.math.BigDecimal;
import java.util.List;

@NoRepositoryBean
public interface ScoreRepository<T, K> extends MongoRepository<T, K> {

    List<T> findByRelevanceGreaterThanEqualOrderByRelevanceAsc(BigDecimal relevance);

    List<T> findByRelevanceLessThanEqualOrderByRelevanceAsc(BigDecimal relevance);
}
