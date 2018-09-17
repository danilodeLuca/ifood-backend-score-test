package ifood.score.entities;

import ifood.score.dtos.ItemInfoDTO;
import ifood.score.dtos.OrderInfoDTO;
import ifood.score.order.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class RelevanceCalculator {

    public static final BigDecimal RELEVANCE_MULTIPLIER = BigDecimal.valueOf(10000);
    public static final int RELEVANCE_SCALE = 9;
    public static final RoundingMode ROUND_MODE = RoundingMode.HALF_EVEN;

    private Integer itemsQuantity;
    private Integer totalItemsOrder;
    private BigDecimal sumItemPrice;
    private BigDecimal orderPrice;

    public static RelevanceCalculator fromItem(ItemInfoDTO item, OrderInfoDTO orderDTO) {
        return new RelevanceCalculator(item.getSize(), orderDTO.getSize(),
                item.getTotalPrice(), orderDTO.getTotalPrice());
    }

    public static RelevanceCalculator fromItems(List<Item> items, OrderInfoDTO orderDTO) {
        ItemInfoDTO itemInfoDTO = new ItemInfoDTO(items);
        return RelevanceCalculator.fromItem(itemInfoDTO, orderDTO);
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
