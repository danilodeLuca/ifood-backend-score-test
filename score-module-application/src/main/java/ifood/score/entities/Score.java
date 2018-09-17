package ifood.score.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Score<T> {

    @Id
    protected T id;

    protected BigDecimal relevance;

    protected Integer quantityItems;

    public Score(T id) {
        this.id = id;
        this.relevance = BigDecimal.ZERO.setScale(RelevanceCalculator.RELEVANCE_SCALE, RelevanceCalculator.ROUND_MODE);
        this.quantityItems = 0;
    }

    public void composeWith(List<RelevanceOrderItem> relevances) {
        relevances.forEach(item -> {
            composeWith(item);
        });
    }

    private void composeWith(RelevanceOrderItem item) {
        BigDecimal newTotal = totalRelevances().add(item.getValue());
        quantityItems++;
        recalculate(newTotal);
    }

    private void recalculate(BigDecimal newTotal) {
        if (quantityItems > 0)
            this.relevance = newTotal.divide(BigDecimal.valueOf(quantityItems), RelevanceCalculator.RELEVANCE_SCALE, RelevanceCalculator.ROUND_MODE);
        else {
            this.relevance = BigDecimal.ZERO.setScale(RelevanceCalculator.RELEVANCE_SCALE, RelevanceCalculator.ROUND_MODE);
        }
    }

    public BigDecimal totalRelevances() {
        return relevance.multiply(BigDecimal.valueOf(quantityItems)).setScale(RelevanceCalculator.RELEVANCE_SCALE, RelevanceCalculator.ROUND_MODE);
    }

    public void decomposeWith(List<RelevanceOrderItem> values) {
        values.forEach(item -> {
            decomposeWith(item);
        });
    }

    public void decomposeWith(RelevanceOrderItem item) {
        BigDecimal newTotal = totalRelevances().subtract(item.getValue());
        quantityItems--;
        recalculate(newTotal);
    }
}
