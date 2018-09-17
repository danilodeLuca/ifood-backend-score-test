package ifood.score.entities;

import ifood.score.menu.Category;
import ifood.score.utils.MathUtils;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static ifood.score.entities.RelevanceCalculatorTest.getPizzaExampleRelevance;
import static ifood.score.entities.RelevanceGroupCalculatorTest.getPizzaRelevanceSecondCase;
import static ifood.score.utils.MathUtils.scale;

public class ScoreTest {

    @Test
    public void testCategoryScoreOneItem() {
        CategoryScore categoryScore = new CategoryScore(Category.BRAZILIAN);
        RelevanceCalculator pizzaExampleRelevance = getPizzaExampleRelevance();

        categoryScore.composeWith(Arrays.asList(RelevanceOrderItem.fromCategory(Category.BRAZILIAN, pizzaExampleRelevance)));

        Assert.assertEquals(pizzaExampleRelevance.calcRelevance(), categoryScore.relevance);
    }

    @Test
    public void testCategoryScoreTwoItem() {
        CategoryScore categoryScore = new CategoryScore(Category.BRAZILIAN);
        RelevanceCalculator pizzaExampleRelevance = getPizzaExampleRelevance();
        RelevanceCalculator pizzaExampleRelevance2 = getPizzaRelevanceSecondCase();

        categoryScore.composeWith(Arrays.asList(RelevanceOrderItem.fromCategory(Category.BRAZILIAN, pizzaExampleRelevance)));
        Assert.assertEquals(scale(BigDecimal.valueOf(58.131835890)),
                pizzaExampleRelevance.calcRelevance());
        Assert.assertEquals(pizzaExampleRelevance.calcRelevance(), categoryScore.getRelevance());

        categoryScore.composeWith(Arrays.asList(RelevanceOrderItem.fromCategory(Category.BRAZILIAN, pizzaExampleRelevance2)));
        Assert.assertEquals(scale(BigDecimal.valueOf(13.8)),
                pizzaExampleRelevance2.calcRelevance());

        Assert.assertEquals(scale(BigDecimal.valueOf(35.965917945)), categoryScore.getRelevance());

    }

    @Test
    public void testDecomposeWith() {
        CategoryScore categoryScore = new CategoryScore(Category.BRAZILIAN);
        RelevanceCalculator pizzaExampleRelevance = getPizzaExampleRelevance();

        categoryScore.composeWith(Arrays.asList(RelevanceOrderItem.fromCategory(Category.BRAZILIAN, pizzaExampleRelevance)));
        Assert.assertEquals(scale(BigDecimal.valueOf(58.131835890)),
                pizzaExampleRelevance.calcRelevance());

        categoryScore.decomposeWith(RelevanceOrderItem.fromCategory(Category.BRAZILIAN, pizzaExampleRelevance));
        Assert.assertEquals(scale(BigDecimal.ZERO), categoryScore.getRelevance());
    }
}
