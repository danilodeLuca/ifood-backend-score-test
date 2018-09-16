package ifood.score.services;

import ifood.score.builders.RelevanceOrderBuilder;
import ifood.score.entities.CategoryScore;
import ifood.score.entities.MenuItemScore;
import ifood.score.order.Order;
import ifood.score.repositories.CategoryScoreRepository;
import ifood.score.repositories.MenuItemScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderRelevanceService {

    @Autowired
    private CategoryScoreRepository categoryScoreRepository;
    @Autowired
    private MenuItemScoreRepository menuItemScoreRepository;

    public void calculateOrderRelevance(Order order) {
        RelevanceOrderBuilder builder = RelevanceOrderBuilder.from(order).build();

        builder.getMapCategoryRelevance().forEach((category, value) -> {
            Optional<CategoryScore> score = categoryScoreRepository.findById(category);
            CategoryScore categoryScore = score.isPresent() ? score.get() : new CategoryScore(category);
            categoryScore.composeWith(value);
            categoryScoreRepository.save(categoryScore);
        });

        builder.getMapMenuRelevance().forEach((menuId, value) -> {
            Optional<MenuItemScore> score = menuItemScoreRepository.findById(menuId);
            MenuItemScore menuScore = score.isPresent() ? score.get() : new MenuItemScore(menuId);
            menuScore.composeWith(value);
            menuItemScoreRepository.save(menuScore);
        });
    }

}
