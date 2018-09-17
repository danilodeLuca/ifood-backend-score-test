package ifood.score.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ScoreRepository<T, K> extends MongoRepository<T, K> {
}
