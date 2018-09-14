package ifood.score.entities;

import ifood.score.order.Item;
import ifood.score.order.Order;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

public class OrderInfoDTOTest {

    @Test
    public void testOrderInfoDTOOneItem() {
        Order order = new Order();
        Item item = new Item();
        item.setMenuUnitPrice(BigDecimal.valueOf(100));
        item.setQuantity(3);
        order.setItems(Arrays.asList(item));

        OrderInfoDTO orderInfoDTO = new OrderInfoDTO(order);
        Assert.assertEquals(3, orderInfoDTO.getSizeOrderItems().intValue());
        Assert.assertEquals(BigDecimal.valueOf(300), orderInfoDTO.getTotalOrderPrice().setScale(0));
    }

    @Test
    public void testOrderInfoDTOAnyItems() {
        Order order = new Order();
        Item item = new Item();
        item.setMenuUnitPrice(BigDecimal.valueOf(100));
        item.setQuantity(3);

        Item item2 = new Item();
        item2.setMenuUnitPrice(BigDecimal.valueOf(20));
        item2.setQuantity(5);

        order.setItems(Arrays.asList(item, item2));

        OrderInfoDTO orderInfoDTO = new OrderInfoDTO(order);
        Assert.assertEquals(8, orderInfoDTO.getSizeOrderItems().intValue());
        Assert.assertEquals(BigDecimal.valueOf(400), orderInfoDTO.getTotalOrderPrice().setScale(0));
    }
}
