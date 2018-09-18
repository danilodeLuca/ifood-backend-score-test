package ifood.score.services;

import ifood.score.entities.Score;
import ifood.score.repositories.ScoreRepository;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public abstract class ScoreService<T extends Score> {

    public abstract ScoreRepository getRepository();

    public List<T> scoreAbove(BigDecimal relevance, Pageable pageable) {
        return getRepository().findByRelevanceGreaterThanEqualOrderByRelevanceAsc(relevance, pageable);
    }

    public List<T> scoreBellow(BigDecimal relevance, Pageable pageable) {
        return getRepository().findByRelevanceLessThanEqualOrderByRelevanceAsc(relevance, pageable);
    }
}
