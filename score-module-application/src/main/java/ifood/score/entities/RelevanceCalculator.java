package ifood.score.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@AllArgsConstructor
public class RelevanceCalculator {

    public static final BigDecimal RELEVANCE_MULTIPLIER = BigDecimal.valueOf(10000);
    public static final int RELEVANCE_SCALE = 9;

    private Integer itemsQuantity;
    private Integer totalItemsOrder;
    private BigDecimal sumItemPrice;
    private BigDecimal orderPrice;

    public BigDecimal calcIQ() {
        Double divide = itemsQuantity * 1.0 / totalItemsOrder;
        return BigDecimal.valueOf(divide);
    }

    public BigDecimal calcIP() {
        return sumItemPrice.divide(orderPrice, RELEVANCE_SCALE, RoundingMode.HALF_EVEN);
    }

    public BigDecimal calcRelevance() {
        Double sqrt = Math.sqrt(calcIQ().multiply(calcIP()).multiply(RELEVANCE_MULTIPLIER).doubleValue());
        return BigDecimal.valueOf(sqrt).setScale(RELEVANCE_SCALE, RoundingMode.HALF_EVEN);
    }

}
