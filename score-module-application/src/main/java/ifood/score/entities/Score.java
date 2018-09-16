package ifood.score.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.List;

@Data
public abstract class Score<T> {

    @Id
    protected T id;

    protected List<RelevanceCalculator> relevances;

    protected BigDecimal value;

    protected Integer quantityItems;

    public Score(T id) {
        this.id = id;
        this.value = BigDecimal.ZERO;
        this.quantityItems = 0;
    }

    public void composeWith(RelevanceCalculator relevance) {
        BigDecimal newTotal = getTotalRelevances().add(relevance.calcRelevance());
        recalculate(newTotal);
    }

    private void recalculate(BigDecimal newTotal) {
        quantityItems++;
        this.value = newTotal.divide(BigDecimal.valueOf(quantityItems), RelevanceCalculator.RELEVANCE_SCALE, RelevanceCalculator.ROUND_MODE);
    }

    public BigDecimal getTotalRelevances() {
        return value.multiply(BigDecimal.valueOf(quantityItems));
    }
}
