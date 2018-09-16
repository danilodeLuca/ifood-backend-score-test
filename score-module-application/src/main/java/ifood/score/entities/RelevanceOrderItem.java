package ifood.score.entities;

import ifood.score.menu.Category;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class RelevanceOrderItem {

    @Indexed
    private Category category;

    @Indexed
    private UUID menuId;

    private BigDecimal value;

    private RelevanceOrderItem(Category id, BigDecimal calcRelevance) {
        this.category = id;
        this.value = calcRelevance;
    }

    public static RelevanceOrderItem fromMenu(UUID id, RelevanceCalculator calc) {
        return new RelevanceOrderItem(id, calc.calcRelevance());
    }

    public static RelevanceOrderItem fromCategory(Category id, RelevanceCalculator calc) {
        return new RelevanceOrderItem(id, calc.calcRelevance());
    }

    private RelevanceOrderItem(UUID id, BigDecimal calcRelevance) {
        this.menuId = id;
        this.value = calcRelevance;
    }
}
