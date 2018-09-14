package ifood.score.entities;

import ifood.score.order.Item;
import ifood.score.order.Order;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderInfoDTO {
    private Integer sizeOrderItems;
    private BigDecimal totalOrderPrice;

    public OrderInfoDTO(Order order) {
        this.sizeOrderItems = order.getItems().stream().mapToInt(Item::getQuantity).sum();
        this.totalOrderPrice = order.getItems().stream()
                .map(Item::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
