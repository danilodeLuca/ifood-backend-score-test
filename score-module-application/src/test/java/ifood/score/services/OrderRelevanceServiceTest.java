package ifood.score.services;

import ifood.score.config.BaseTest;
import ifood.score.entities.*;
import ifood.score.menu.Category;
import ifood.score.mock.generator.order.OrderPicker;
import ifood.score.order.Order;
import ifood.score.repositories.CategoryScoreRepository;
import ifood.score.repositories.MenuItemScoreRepository;
import ifood.score.repositories.RelevanceOrderRepository;
import ifood.score.utils.MathUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static ifood.score.utils.MathUtils.scale;

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
            Assert.assertNotNull(categoryScore.getRelevance());
            Assert.assertNotEquals(BigDecimal.ZERO, categoryScore.getRelevance());
        });

        Map<UUID, List<RelevanceOrderItem>> menuMap = orderSaved.getRelevances().stream().filter(i -> i.getMenuId() != null).collect(Collectors.groupingBy(RelevanceOrderItem::getMenuId));
        menuMap.keySet().forEach(k -> {
            MenuItemScore menuScore = menuItemScoreRepository.findById(k).get();
            Assert.assertNotNull(menuScore.getRelevance());
            Assert.assertNotEquals(BigDecimal.ZERO, menuScore.getRelevance());
        });

        service.cancelOrder(order.getUuid().toString());

        orderSaved = relevanceOrderRepository.findById(order.getUuid()).get();
        Assert.assertTrue(orderSaved.isCancelled());

        categoryMap = orderSaved.getRelevances().stream().filter(i -> i.getCategory() != null).collect(Collectors.groupingBy(RelevanceOrderItem::getCategory));
        categoryMap.keySet().forEach(k -> {
            CategoryScore categoryScore = categoryScoreRepository.findById(k).get();
            Assert.assertNotNull(categoryScore.getRelevance());
            Assert.assertEquals(scale(BigDecimal.ZERO), categoryScore.getRelevance());
        });

        menuMap = orderSaved.getRelevances().stream().filter(i -> i.getMenuId() != null).collect(Collectors.groupingBy(RelevanceOrderItem::getMenuId));
        menuMap.keySet().forEach(k -> {
            MenuItemScore menuScore = menuItemScoreRepository.findById(k).get();
            Assert.assertNotNull(menuScore.getRelevance());
            Assert.assertEquals(scale(BigDecimal.ZERO), menuScore.getRelevance());
        });
    }

    @Test
    public void testexpireOrdersBeforeDate() {
        Order order = picker.pick();
        order.setConfirmedAt(DateTime.now().minusDays(32).toDate());
        service.checkoutOrderAndCalculateRelevance(order);

        Order order2 = picker.pick();
        order2.setConfirmedAt(DateTime.now().minusDays(26).toDate());
        service.checkoutOrderAndCalculateRelevance(order2);

        Date dateToExpire = DateTime.now().minusDays(30).toDate();

        service.expireOrdersBeforeDate(dateToExpire);

        RelevanceOrder orderSaved1 = relevanceOrderRepository.findById(order.getUuid()).get();
        Assert.assertTrue(orderSaved1.isExpired());

        RelevanceOrder orderSaved2 = relevanceOrderRepository.findById(order2.getUuid()).get();
        Assert.assertFalse(orderSaved2.isExpired());
        Assert.assertTrue(orderSaved2.isCheckout());
    }
}
