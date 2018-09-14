package ifood.score.entities;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class RelevanceCalculatorTest {

    @Test
    public void testCalcIq() {
        RelevanceCalculator pizzaExampleRelevance = getPizzaExampleRelevance();

        BigDecimal IQ = pizzaExampleRelevance.calcIQ();
        Assert.assertEquals(BigDecimal.valueOf(0.4), IQ);
    }

    @Test
    public void testCalcIp() {
        RelevanceCalculator pizzaExampleRelevance = getPizzaExampleRelevance();

        BigDecimal IQ = pizzaExampleRelevance.calcIP();
        Assert.assertEquals(BigDecimal.valueOf(0.844827586), IQ);
    }

    @Test
    public void testRelevance() {
        RelevanceCalculator pizzaExampleRelevance = getPizzaExampleRelevance();
        BigDecimal relevance = pizzaExampleRelevance.calcRelevance();
        Assert.assertEquals(BigDecimal.valueOf(58.131835890).setScale(RelevanceCalculator.RELEVANCE_SCALE), relevance);
    }

    public static RelevanceCalculator getPizzaExampleRelevance() {
        int itemsQuantity = 2; // totals of pizza in example
        int totalItemsOrder = 5; // totals of itens 2 pizza + 3 vegan
        BigDecimal sumItemPrice = BigDecimal.valueOf(49);// 1*26+1*23
        BigDecimal orderPrice = BigDecimal.valueOf(58);// 1*26+1*23+3*3
        return new RelevanceCalculator(itemsQuantity, totalItemsOrder, sumItemPrice, orderPrice);
    }
}
