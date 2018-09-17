package ifood.score.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Score<T> {

    @Id
    protected T id;

    protected BigDecimal value;

    protected Integer quantityItems;

    public Score(T id) {
        this.id = id;
        this.value = BigDecimal.ZERO;
        this.quantityItems = 0;
    }

    public void composeWith(List<RelevanceOrderItem> relevances) {
        relevances.forEach(item -> {
            composeWith(item);
        });
    }

    private void composeWith(RelevanceOrderItem item) {
        BigDecimal newTotal = getTotalRelevances().add(item.getValue());
        quantityItems++;
        recalculate(newTotal);
    }

    private void recalculate(BigDecimal newTotal) {
        if (quantityItems > 0)
            this.value = newTotal.divide(BigDecimal.valueOf(quantityItems), RelevanceCalculator.RELEVANCE_SCALE, RelevanceCalculator.ROUND_MODE);
        else {
            this.value = BigDecimal.ZERO;
        }
    }

    public BigDecimal getTotalRelevances() {
        return value.multiply(BigDecimal.valueOf(quantityItems));
    }

    public void decomposeWith(List<RelevanceOrderItem> values) {
        values.forEach(item -> {
            decomposeWith(item);
        });
    }

    public void decomposeWith(RelevanceOrderItem item) {
        BigDecimal newTotal = getTotalRelevances().subtract(item.getValue());
        quantityItems--;
        recalculate(newTotal);
    }
}
