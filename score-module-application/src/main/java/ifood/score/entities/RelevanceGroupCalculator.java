package ifood.score.entities;

import ifood.score.order.Item;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class RelevanceGroupCalculator {

    private List<RelevanceCalculator> itemsRelevances;

    public BigDecimal calcScore() {
        BigDecimal totalRelevances = itemsRelevances.parallelStream()
                .map(RelevanceCalculator::calcRelevance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal size = BigDecimal.valueOf(itemsRelevances.size());
        return totalRelevances.divide(size, RelevanceCalculator.RELEVANCE_SCALE, RelevanceCalculator.ROUND_MODE);
    }

    public static RelevanceGroupCalculator fromItems(List<Item> items, OrderInfoDTO orderDTO) {
        List<RelevanceCalculator> relevanceList = items.stream().map(item -> RelevanceCalculator.fromItem(item, orderDTO)).collect(Collectors.toList());
        return new RelevanceGroupCalculator(relevanceList);
    }
}
