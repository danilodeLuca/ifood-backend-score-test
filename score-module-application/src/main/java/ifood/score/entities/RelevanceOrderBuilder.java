package ifood.score.entities;

import ifood.score.exceptions.NoItemsInOrderException;
import ifood.score.menu.Category;
import ifood.score.order.Item;
import ifood.score.order.Order;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class RelevanceOrderBuilder {

    private Order order;

    private OrderInfoDTO orderInfoDTO;

    private Map<UUID, RelevanceCalculator> mapMenuRelevance = new HashMap<>();

    private Map<Category, RelevanceCalculator> mapCategoryRelevance = new HashMap<>();

    private RelevanceOrderBuilder(Order order) {
        this.order = order;
        this.orderInfoDTO = new OrderInfoDTO(order);
    }

    public static RelevanceOrderBuilder from(Order order) {
        if (order.getItems() == null || order.getItems().isEmpty())
            throw new NoItemsInOrderException("Order with id " + order.getUuid() + " do not have items!!");

        return new RelevanceOrderBuilder(order);
    }

    public RelevanceOrderBuilder build() {
        mapMenuRelevance = collectMapByFunction(Item::getMenuUuid);
        mapCategoryRelevance = collectMapByFunction(Item::getMenuCategory);
        return this;
    }

    private <T> Map<T, RelevanceCalculator> collectMapByFunction(Function<Item, T> groupingByMenuUUId) {
        Map<T, List<Item>> grouped = order.getItems().stream()
                .collect(Collectors.groupingBy(groupingByMenuUUId));
        return grouped.entrySet().stream()
                .collect(Collectors.toMap(obj -> obj.getKey(),
                        obj -> RelevanceCalculator.fromItems(obj.getValue(), this.orderInfoDTO)));
    }

}
