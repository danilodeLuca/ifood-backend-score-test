package ifood.score.entities;

import ifood.score.utils.MathUtils;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.List;

import static ifood.score.utils.MathUtils.scale;

@Data
public class Score<T> {

    @Id
    protected T id;

    protected BigDecimal relevance;

    protected Integer quantityItems;

    public Score(T id) {
        this.id = id;
        this.relevance = scale(BigDecimal.ZERO);
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
            this.relevance = newTotal.divide(BigDecimal.valueOf(quantityItems), MathUtils.RELEVANCE_SCALE, MathUtils.ROUND_MODE);
        else {
            this.relevance = scale(BigDecimal.ZERO);
        }
    }

    public BigDecimal totalRelevances() {
        return scale(relevance.multiply(BigDecimal.valueOf(quantityItems)));
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
