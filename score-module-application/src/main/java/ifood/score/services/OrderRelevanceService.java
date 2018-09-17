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

        new RelevanceProcessor<Category>().checkout(relevanceOrder.getCategoryMapRelevances(), categoryScoreRepository);

        new RelevanceProcessor<UUID>().checkout(relevanceOrder.getMenuMapRelevances(), menuItemScoreRepository);

        checkoutOrder(relevanceOrder);
    }

    private void checkoutOrder(RelevanceOrder relevanceOrder) {
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
                Score savedScore = score.isPresent() ? score.get() : getScore(k);
                savedScore.composeWith(values);
                repo.save(savedScore);
            });
        }

        private Score getScore(T k) {
            if (k instanceof Category)
                return new CategoryScore((Category) k);
            else
                return new MenuItemScore((UUID) k);
        }

    }
}
