package ifood.score.dtos;

import ifood.score.order.Item;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ItemInfoDTO extends CommonInfoDTO {

    public ItemInfoDTO(List<Item> list) {
        super(list.stream().mapToInt(Item::getQuantity).sum(), list.stream()
                .map(Item::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }
}
