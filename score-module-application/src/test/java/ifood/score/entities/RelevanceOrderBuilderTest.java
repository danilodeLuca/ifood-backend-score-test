package ifood.score.entities;

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
        Assert.assertNotNull(relevanceOrder.getMapMenuRelevance().get(menuUuid).calcScore());

        Assert.assertEquals(1, relevanceOrder.getMapCategoryRelevance().size());
        Assert.assertTrue(relevanceOrder.getMapCategoryRelevance().containsKey(Category.PIZZA));
        Assert.assertNotNull(relevanceOrder.getMapCategoryRelevance().get(Category.PIZZA).calcScore());
    }
}
