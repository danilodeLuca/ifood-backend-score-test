package ifood.score.entities;

import ifood.score.order.Item;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@AllArgsConstructor
public class RelevanceCalculator {

    public static final BigDecimal RELEVANCE_MULTIPLIER = BigDecimal.valueOf(10000);
    public static final int RELEVANCE_SCALE = 9;
    public static final RoundingMode ROUND_MODE = RoundingMode.HALF_EVEN;

    private Integer itemsQuantity;
    private Integer totalItemsOrder;
    private BigDecimal sumItemPrice;
    private BigDecimal orderPrice;

    public static RelevanceCalculator fromItem(Item item, OrderInfoDTO orderDTO) {
        BigDecimal totalItem = item.getTotal();
        return new RelevanceCalculator(item.getQuantity(), orderDTO.getSizeOrderItems(),
                totalItem, orderDTO.getTotalOrderPrice());
    }

    public BigDecimal calcIQ() {
        Double divide = itemsQuantity * 1.0 / totalItemsOrder;
        return BigDecimal.valueOf(divide);
    }

    public BigDecimal calcIP() {
        return sumItemPrice.divide(orderPrice, RELEVANCE_SCALE, ROUND_MODE);
    }

    public BigDecimal calcRelevance() {
        Double sqrt = Math.sqrt(calcIQ().multiply(calcIP()).multiply(RELEVANCE_MULTIPLIER).doubleValue());
        return BigDecimal.valueOf(sqrt).setScale(RELEVANCE_SCALE, RelevanceCalculator.ROUND_MODE);
    }

}
