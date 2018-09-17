package ifood.score.entities;

import ifood.score.utils.MathUtils;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static ifood.score.utils.MathUtils.scale;

public class RelevanceGroupCalculatorTest {

    @Test
    public void testRelevanceGroupCalculatorOneItem() {
        RelevanceCalculator pizza = RelevanceCalculatorTest.getPizzaExampleRelevance();
        RelevanceGroupCalculator relevanceGroupCalculator = new RelevanceGroupCalculator(Arrays.asList(pizza));

        Assert.assertEquals(pizza.calcRelevance(), relevanceGroupCalculator.calcScore());
    }

    @Test
    public void testRelevanceGroupCalculatorAnyItems() {
        RelevanceCalculator pizza = RelevanceCalculatorTest.getPizzaExampleRelevance();
        RelevanceCalculator pizza2 = getPizzaRelevanceSecondCase();

        Assert.assertEquals(scale(BigDecimal.valueOf(13.8)), pizza2.calcRelevance());

        List<RelevanceCalculator> itemsRelevances = Arrays.asList(pizza, pizza2);
        RelevanceGroupCalculator relevanceGroupCalculator = new RelevanceGroupCalculator(itemsRelevances);

        Assert.assertEquals(BigDecimal.valueOf(35.965917945), relevanceGroupCalculator.calcScore());
    }

    public static RelevanceCalculator getPizzaRelevanceSecondCase() {
        int itemsQuantity = 138;
        int totalItemsOrder = 1;
        BigDecimal sumItemPrice = BigDecimal.valueOf(1000);
        BigDecimal orderPrice = BigDecimal.valueOf(1);
        return new RelevanceCalculator(itemsQuantity, totalItemsOrder, sumItemPrice, orderPrice) {
            @Override
            public BigDecimal calcRelevance() {
                return scale(BigDecimal.valueOf(13.8));
            }
        };
    }
}
