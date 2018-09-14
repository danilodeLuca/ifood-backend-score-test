package ifood.score.entities;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

public class RelevanceGroupCalculatorTest {

    @Test
    public void testRelevanceGroupCalculatorOneItem() {
        RelevanceCalculator pizza = RelevanceCalculatorTest.getPizzaExampleRelevance();
        List<RelevanceCalculator> itemsRelevances = Arrays.asList(pizza);
        RelevanceGroupCalculator relevanceGroupCalculator = new RelevanceGroupCalculator(itemsRelevances);

        Assert.assertEquals(pizza.calcRelevance(), relevanceGroupCalculator.calcScore());
    }

    @Test
    public void testRelevanceGroupCalculatorAnyItems() {
        RelevanceCalculator pizza = RelevanceCalculatorTest.getPizzaExampleRelevance();
        RelevanceCalculator pizza2 = getPizzaRelevanceSecondCase();

        Assert.assertEquals(BigDecimal.valueOf(13.8).setScale(RelevanceCalculator.RELEVANCE_SCALE), pizza2.calcRelevance());

        List<RelevanceCalculator> itemsRelevances = Arrays.asList(pizza, pizza2);
        RelevanceGroupCalculator relevanceGroupCalculator = new RelevanceGroupCalculator(itemsRelevances);

        Assert.assertEquals(BigDecimal.valueOf(35.965917945), relevanceGroupCalculator.calcScore());
    }

    private RelevanceCalculator getPizzaRelevanceSecondCase() {
        int itemsQuantity = 138;
        int totalItemsOrder = 1;
        BigDecimal sumItemPrice = BigDecimal.valueOf(1000);
        BigDecimal orderPrice = BigDecimal.valueOf(1);
        return new RelevanceCalculator(itemsQuantity, totalItemsOrder, sumItemPrice, orderPrice) {
            @Override
            public BigDecimal calcRelevance() {
                return BigDecimal.valueOf(13.8).setScale(RelevanceCalculator.RELEVANCE_SCALE, RoundingMode.HALF_EVEN);
            }
        };
    }
}
