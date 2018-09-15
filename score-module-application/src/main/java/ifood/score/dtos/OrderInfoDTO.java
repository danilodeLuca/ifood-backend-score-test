package ifood.score.dtos;

import ifood.score.order.Item;
import ifood.score.order.Order;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderInfoDTO extends CommonInfoDTO {

    public OrderInfoDTO(Order order) {
        super(order.getItems().stream().mapToInt(Item::getQuantity).sum(), order.getItems().stream()
                .map(Item::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }
}
