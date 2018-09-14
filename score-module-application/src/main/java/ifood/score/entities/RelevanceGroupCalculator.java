package ifood.score.entities;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
public class RelevanceGroupCalculator {

    private List<RelevanceCalculator> itemsRelevances;

    public BigDecimal calcScore() {
        BigDecimal totalRelevances = itemsRelevances.parallelStream()
                .map(RelevanceCalculator::calcRelevance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal size = BigDecimal.valueOf(itemsRelevances.size());
        return totalRelevances.divide(size, RelevanceCalculator.RELEVANCE_SCALE, RelevanceCalculator.ROUND_MODE);
    }

}
