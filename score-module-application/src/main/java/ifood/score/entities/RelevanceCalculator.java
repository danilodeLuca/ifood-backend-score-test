package ifood.score.entities;

import ifood.score.dtos.ItemInfoDTO;
import ifood.score.dtos.OrderInfoDTO;
import ifood.score.order.Item;
import ifood.score.utils.MathUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

import static ifood.score.utils.MathUtils.scale;

@AllArgsConstructor
@Getter
@ToString
public class RelevanceCalculator {

    public static final BigDecimal RELEVANCE_MULTIPLIER = BigDecimal.valueOf(10000);

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
        return sumItemPrice.divide(orderPrice, MathUtils.RELEVANCE_SCALE, MathUtils.ROUND_MODE);
    }

    public BigDecimal calcRelevance() {
        Double sqrt = Math.sqrt(calcIQ().multiply(calcIP()).multiply(RELEVANCE_MULTIPLIER).doubleValue());
        return scale(BigDecimal.valueOf(sqrt));
    }

}
