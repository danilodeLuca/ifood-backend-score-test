package ifood.score.services;

import ifood.score.config.BaseTest;
import ifood.score.entities.CategoryScore;
import ifood.score.entities.MenuItemScore;
import ifood.score.entities.RelevanceOrder;
import ifood.score.entities.RelevanceOrderItem;
import ifood.score.menu.Category;
import ifood.score.mock.generator.order.OrderPicker;
import ifood.score.order.Order;
import ifood.score.repositories.CategoryScoreRepository;
import ifood.score.repositories.MenuItemScoreRepository;
import ifood.score.repositories.RelevanceOrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderRelevanceServiceTest extends BaseTest {

    @Autowired
    private OrderRelevanceService service;
    @Autowired
    private RelevanceOrderRepository relevanceOrderRepository;
    @Autowired
    private CategoryScoreRepository categoryScoreRepository;
    @Autowired
    private MenuItemScoreRepository menuItemScoreRepository;

    private static OrderPicker picker = new OrderPicker();

    @Test
    public void testCalculateOrderRelevance() {
        Order order = picker.pick();

        service.checkoutOrderAndCalculateRelevance(order);
        Optional<RelevanceOrder> orderSaved = relevanceOrderRepository.findById(order.getUuid());
        Assert.assertTrue(orderSaved.isPresent());

        Assert.assertTrue(orderSaved.get().isCheckout());
    }

    @Test
    public void testCancelOrderNoOrder() {
        Order order = picker.pick();
        service.checkoutOrderAndCalculateRelevance(order);
        RelevanceOrder orderSaved = relevanceOrderRepository.findById(order.getUuid()).get();
        Map<Category, List<RelevanceOrderItem>> categoryMap = orderSaved.getRelevances().stream().filter(i -> i.getCategory() != null).collect(Collectors.groupingBy(RelevanceOrderItem::getCategory));
        categoryMap.keySet().forEach(k -> {
            CategoryScore categoryScore = categoryScoreRepository.findById(k).get();
            Assert.assertNotNull(categoryScore.getValue());
            Assert.assertNotEquals(BigDecimal.ZERO, categoryScore.getValue());
        });

        Map<UUID, List<RelevanceOrderItem>> menuMap = orderSaved.getRelevances().stream().filter(i -> i.getMenuId() != null).collect(Collectors.groupingBy(RelevanceOrderItem::getMenuId));
        menuMap.keySet().forEach(k -> {
            MenuItemScore menuScore = menuItemScoreRepository.findById(k).get();
            Assert.assertNotNull(menuScore.getValue());
            Assert.assertNotEquals(BigDecimal.ZERO, menuScore.getValue());
        });

        service.cancelOrder(order.getUuid().toString());

        orderSaved = relevanceOrderRepository.findById(order.getUuid()).get();
        Assert.assertTrue(orderSaved.isCancelled());

        categoryMap = orderSaved.getRelevances().stream().filter(i -> i.getCategory() != null).collect(Collectors.groupingBy(RelevanceOrderItem::getCategory));
        categoryMap.keySet().forEach(k -> {
            CategoryScore categoryScore = categoryScoreRepository.findById(k).get();
            Assert.assertNotNull(categoryScore.getValue());
            Assert.assertEquals(BigDecimal.ZERO, categoryScore.getValue());
        });

        menuMap = orderSaved.getRelevances().stream().filter(i -> i.getMenuId() != null).collect(Collectors.groupingBy(RelevanceOrderItem::getMenuId));
        menuMap.keySet().forEach(k -> {
            MenuItemScore menuScore = menuItemScoreRepository.findById(k).get();
            Assert.assertNotNull(menuScore.getValue());
            Assert.assertEquals(BigDecimal.ZERO, menuScore.getValue());
        });

    }
}
