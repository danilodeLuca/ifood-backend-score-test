package ifood.score.entities;

import ifood.score.menu.Category;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Document
@Data
public class RelevanceOrder {

    @Id
    private UUID orderId;

    private Date confirmationDate;

    private RelevanceStatus status;

    private List<RelevanceOrderItem> relevances;

    public boolean isCheckout() {
        return RelevanceStatus.CHECKOUT.equals(status);
    }

    public boolean isCancelled() {
        return RelevanceStatus.CANCELLED.equals(status);
    }

    public Map<Category, List<RelevanceOrderItem>> getCategoryMapRelevances() {
        Predicate<RelevanceOrderItem> filter = i -> i.getCategory() != null;
        Function<RelevanceOrderItem, Category> grouping = RelevanceOrderItem::getCategory;
        return getMappedRelevances(grouping, filter);
    }

    public Map<UUID, List<RelevanceOrderItem>> getMenuMapRelevances() {
        Predicate<RelevanceOrderItem> filter = i -> i.getMenuId() != null;
        Function<RelevanceOrderItem, UUID> grouping = RelevanceOrderItem::getMenuId;
        return getMappedRelevances(grouping, filter);
    }

    private <T> Map<T, List<RelevanceOrderItem>> getMappedRelevances(Function<RelevanceOrderItem, T> grouping, Predicate<RelevanceOrderItem> filter) {
        return getRelevances().stream().filter(filter).collect(Collectors.groupingBy(grouping));
    }

    public enum RelevanceStatus {
        CHECKOUT, CANCELLED, EXPIRED;
    }
}
