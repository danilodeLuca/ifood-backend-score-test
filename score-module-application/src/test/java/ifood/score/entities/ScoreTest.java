package ifood.score.entities;

import ifood.score.menu.Category;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static ifood.score.entities.RelevanceCalculatorTest.getPizzaExampleRelevance;
import static ifood.score.entities.RelevanceGroupCalculatorTest.getPizzaRelevanceSecondCase;

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
        Assert.assertEquals(BigDecimal.valueOf(58.131835890).setScale(RelevanceCalculator.RELEVANCE_SCALE),
                pizzaExampleRelevance.calcRelevance());
        Assert.assertEquals(pizzaExampleRelevance.calcRelevance(), categoryScore.getRelevance());

        categoryScore.composeWith(Arrays.asList(RelevanceOrderItem.fromCategory(Category.BRAZILIAN, pizzaExampleRelevance2)));
        Assert.assertEquals(BigDecimal.valueOf(13.8).setScale(RelevanceCalculator.RELEVANCE_SCALE),
                pizzaExampleRelevance2.calcRelevance());

        Assert.assertEquals(BigDecimal.valueOf(35.965917945).setScale(RelevanceCalculator.RELEVANCE_SCALE), categoryScore.getRelevance());

    }

    @Test
    public void testDecomposeWith() {
        CategoryScore categoryScore = new CategoryScore(Category.BRAZILIAN);
        RelevanceCalculator pizzaExampleRelevance = getPizzaExampleRelevance();

        categoryScore.composeWith(Arrays.asList(RelevanceOrderItem.fromCategory(Category.BRAZILIAN, pizzaExampleRelevance)));
        Assert.assertEquals(BigDecimal.valueOf(58.131835890).setScale(RelevanceCalculator.RELEVANCE_SCALE),
                pizzaExampleRelevance.calcRelevance());

        categoryScore.decomposeWith(RelevanceOrderItem.fromCategory(Category.BRAZILIAN, pizzaExampleRelevance));
        Assert.assertEquals(BigDecimal.ZERO, categoryScore.getRelevance());
    }
}
