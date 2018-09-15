package ifood.score.entities;

import ifood.score.builders.RelevanceOrderBuilder;
import ifood.score.dtos.OrderInfoDTO;
import ifood.score.exceptions.NoItemsInOrderException;
import ifood.score.menu.Category;
import ifood.score.order.Item;
import ifood.score.order.Order;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class RelevanceOrderBuilderTest {

    @Test(expected = NoItemsInOrderException.class)
    public void testBuilderOrderNoItens() {
        Order order = new Order();
        RelevanceOrderBuilder.from(order).build();
    }

    @Test
    public void testBuilderOneItem() {
        UUID menuUuid = UUID.randomUUID();

        Order order = new Order();
        order.setUuid(UUID.randomUUID());
        order.setRestaurantUuid(UUID.randomUUID());
        order.setCustomerUuid(UUID.randomUUID());
        order.setAddressUuid(UUID.randomUUID());
        order.setConfirmedAt(new Date());

        Item item = new Item();
        item.setQuantity(2);
        item.setMenuCategory(Category.PIZZA);
        item.setMenuUuid(menuUuid);
        item.setMenuUnitPrice(BigDecimal.valueOf(40));

        order.setItems(Arrays.asList(item));

        RelevanceOrderBuilder relevanceOrder = RelevanceOrderBuilder.from(order).build();

        Assert.assertEquals(1, relevanceOrder.getMapMenuRelevance().size());
        Assert.assertTrue(relevanceOrder.getMapMenuRelevance().containsKey(menuUuid));
        Assert.assertNotNull(relevanceOrder.getMapMenuRelevance().get(menuUuid).calcRelevance());

        Assert.assertEquals(1, relevanceOrder.getMapCategoryRelevance().size());
        Assert.assertTrue(relevanceOrder.getMapCategoryRelevance().containsKey(Category.PIZZA));
        Assert.assertNotNull(relevanceOrder.getMapCategoryRelevance().get(Category.PIZZA).calcRelevance());
    }

    /**
     * this follow the order example values from the Score Test
     */
    @Test
    public void testBuilderAnyItens() {
        Order order = new Order();
        order.setUuid(UUID.fromString("c3850c73-f4ca-4974-871c-6f99c6167f1f"));
        order.setRestaurantUuid(UUID.fromString("836dc1c1-aec7-4272-ab1e-ba01a9842ede"));
        order.setCustomerUuid(UUID.fromString("10359eaa-2292-4217-a7fe-45172be9b498"));
        order.setAddressUuid(UUID.fromString("789224b0-5cee-48b8-89ec-13001d955391"));
        order.setConfirmedAt(new Date());

        UUID menuPizza1 = UUID.fromString("dad0f8ac-9433-40fd-bd43-9ec0c12d5213");
        UUID menuVegan = UUID.fromString("6208e2fd-45c3-4013-a69a-5f54cb249be0");
        UUID menuPizza2 = UUID.fromString("bd2746ce-a975-4bf4-84dc-fedd14273a03");

        Item pizza1 = new Item();
        pizza1.setMenuUuid(menuPizza1);
        pizza1.setMenuCategory(Category.PIZZA);
        pizza1.setMenuUnitPrice(BigDecimal.valueOf(26));
        pizza1.setQuantity(1);

        Item vegan = new Item();
        vegan.setMenuUuid(menuVegan);
        vegan.setMenuCategory(Category.VEGAN);
        vegan.setMenuUnitPrice(BigDecimal.valueOf(3));
        vegan.setQuantity(3);

        Item pizza2 = new Item();
        pizza2.setMenuUuid(menuPizza2);
        pizza2.setMenuCategory(Category.PIZZA);
        pizza2.setMenuUnitPrice(BigDecimal.valueOf(23));
        pizza2.setQuantity(1);

        order.setItems(Arrays.asList(pizza1, vegan, pizza2));

        RelevanceOrderBuilder relevanceOrder = RelevanceOrderBuilder.from(order).build();

        Assert.assertEquals(3, relevanceOrder.getMapMenuRelevance().size());
        Assert.assertTrue(relevanceOrder.getMapMenuRelevance().containsKey(menuPizza1));
        Assert.assertTrue(relevanceOrder.getMapMenuRelevance().containsKey(menuVegan));
        Assert.assertTrue(relevanceOrder.getMapMenuRelevance().containsKey(menuPizza2));

        BigDecimal menuVeganScore = relevanceOrder.getMapMenuRelevance().get(menuVegan).calcRelevance();
        Assert.assertNotNull(menuVeganScore);
        Assert.assertEquals(BigDecimal.valueOf(30.512857683), menuVeganScore);

        Assert.assertNotNull(relevanceOrder.getMapMenuRelevance().get(menuPizza1).calcRelevance());
        Assert.assertNotNull(relevanceOrder.getMapMenuRelevance().get(menuPizza2).calcRelevance());

        Assert.assertEquals(2, relevanceOrder.getMapCategoryRelevance().size());
        Assert.assertTrue(relevanceOrder.getMapCategoryRelevance().containsKey(Category.PIZZA));
        Assert.assertTrue(relevanceOrder.getMapCategoryRelevance().containsKey(Category.VEGAN));

        RelevanceCalculator.fromItems(Arrays.asList(pizza1, pizza2), new OrderInfoDTO(order)).calcRelevance();

        BigDecimal categoryPizzaScore = relevanceOrder.getMapCategoryRelevance().get(Category.PIZZA).calcRelevance();
        Assert.assertNotNull(categoryPizzaScore);
        Assert.assertEquals(BigDecimal.valueOf(58.131835890).setScale(9), categoryPizzaScore);
    }
}
