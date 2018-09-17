package ifood.score.entities;

import ifood.score.dtos.ItemInfoDTO;
import ifood.score.dtos.OrderInfoDTO;
import ifood.score.order.Item;
import ifood.score.order.Order;
import ifood.score.utils.MathUtils;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static ifood.score.utils.MathUtils.scale;

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
        Assert.assertEquals(scale(BigDecimal.valueOf(58.131835890)), relevance);
    }

    public static RelevanceCalculator getPizzaExampleRelevance() {
        int itemsQuantity = 2; // totals of pizza in example
        int totalItemsOrder = 5; // totals of itens 2 pizza + 3 vegan
        BigDecimal sumItemPrice = BigDecimal.valueOf(49);// 1*26+1*23
        BigDecimal orderPrice = BigDecimal.valueOf(58);// 1*26+1*23+3*3
        return new RelevanceCalculator(itemsQuantity, totalItemsOrder, sumItemPrice, orderPrice);
    }

    @Test
    public void testResult() {
        Item item = new Item();
        item.setQuantity(1);
        item.setMenuUnitPrice(BigDecimal.valueOf(20));
        ItemInfoDTO itemDTO = new ItemInfoDTO(Arrays.asList(item));

        Order order = new Order();
        order.setItems(Arrays.asList(item));
        OrderInfoDTO orderDTO = new OrderInfoDTO(order);
        RelevanceCalculator a = RelevanceCalculator.fromItem(itemDTO, orderDTO);

    }
}
