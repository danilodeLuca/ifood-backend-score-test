package ifood.score.services;

import ifood.score.entities.Score;
import ifood.score.repositories.ScoreRepository;

import java.math.BigDecimal;
import java.util.List;

public abstract class ScoreService<T extends Score> {

    public abstract ScoreRepository getRepository();

    public List<T> scoreAbove(BigDecimal relevance) {
        return getRepository().findByRelevanceGreaterThanEqualOrderByRelevanceAsc(relevance);
    }

    public List<T> scoreBellow(BigDecimal relevance) {
        return getRepository().findByRelevanceLessThanEqualOrderByRelevanceAsc(relevance);
    }
}
