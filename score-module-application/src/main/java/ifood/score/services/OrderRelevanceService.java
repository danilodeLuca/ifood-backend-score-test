package ifood.score.services;

import ifood.score.builders.RelevanceOrderBuilder;
import ifood.score.entities.*;
import ifood.score.menu.Category;
import ifood.score.order.Order;
import ifood.score.repositories.CategoryScoreRepository;
import ifood.score.repositories.MenuItemScoreRepository;
import ifood.score.repositories.RelevanceOrderRepository;
import ifood.score.repositories.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderRelevanceService {

    @Autowired
    private CategoryScoreRepository categoryScoreRepository;
    @Autowired
    private MenuItemScoreRepository menuItemScoreRepository;
    @Autowired
    private RelevanceOrderRepository relevanceOrderRepository;

    public void checkoutOrderAndCalculateRelevance(Order order) {
        RelevanceOrderBuilder builder = RelevanceOrderBuilder.from(order).build();
        RelevanceOrder relevanceOrder = builder.getRelevanceOrder();

        relevanceOrder.getCategoryMapRelevances().forEach((category, value) -> {
            Optional<CategoryScore> score = categoryScoreRepository.findById(category);
            CategoryScore categoryScore = score.isPresent() ? score.get() : new CategoryScore(category);
            categoryScore.composeWith(value);
            categoryScoreRepository.save(categoryScore);
        });

        relevanceOrder.getMenuMapRelevances().forEach((menuId, value) -> {
            Optional<MenuItemScore> score = menuItemScoreRepository.findById(menuId);
            MenuItemScore menuScore = score.isPresent() ? score.get() : new MenuItemScore(menuId);
            menuScore.composeWith(value);
            menuItemScoreRepository.save(menuScore);
        });

        checkoutOrder(builder);
    }

    private void checkoutOrder(RelevanceOrderBuilder builder) {
        RelevanceOrder relevanceOrder = builder.getRelevanceOrder();
        relevanceOrder.setStatus(RelevanceOrder.RelevanceStatus.CHECKOUT);
        relevanceOrderRepository.save(relevanceOrder);
    }

    public void cancelOrder(String orderId) {
        Optional<RelevanceOrder> orderSaved = relevanceOrderRepository.findById(UUID.fromString(orderId));
        if (orderSaved.isPresent()) {
            RelevanceOrder relevanceOrder = orderSaved.get();
            if (relevanceOrder.isCheckout()) {
                Map<Category, List<RelevanceOrderItem>> categoryMap = relevanceOrder.getCategoryMapRelevances();
                new RelevanceProcessor<Category>().cancel(categoryMap, categoryScoreRepository);

                Map<UUID, List<RelevanceOrderItem>> menuIdMap = relevanceOrder.getMenuMapRelevances();
                new RelevanceProcessor<UUID>().cancel(menuIdMap, menuItemScoreRepository);

                relevanceOrder.setStatus(RelevanceOrder.RelevanceStatus.CANCELLED);
                relevanceOrderRepository.save(relevanceOrder);
            }
        }
    }

    class RelevanceProcessor<T> {

        public void cancel(Map<T, List<RelevanceOrderItem>> map, ScoreRepository repo) {
            map.forEach((k, values) -> {
                Optional<Score<T>> score = repo.findById(k);
                if (score.isPresent()) {
                    Score<T> savedMenu = score.get();
                    savedMenu.decomposeWith(values);
                    repo.save(savedMenu);
                }
            });
        }

        public void checkout(Map<T, List<RelevanceOrderItem>> map, ScoreRepository repo) {
            map.forEach((k, values) -> {
                Optional<Score<T>> score = repo.findById(k);
                if (score.isPresent()) {
                    Score<T> savedMenu = score.get();
                    savedMenu.decomposeWith(values);
                    repo.save(savedMenu);
                }
            });
        }

    }
}
