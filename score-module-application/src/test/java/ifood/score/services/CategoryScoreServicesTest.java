package ifood.score.services;

import ifood.score.config.BaseTest;
import ifood.score.entities.CategoryScore;
import ifood.score.exceptions.CategoryScoreNotFoundException;
import ifood.score.menu.Category;
import ifood.score.repositories.CategoryScoreRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

public class CategoryScoreServicesTest extends BaseTest {

    @Autowired
    private CategoryScoreRepository categoryScoreRepository;
    @Autowired
    private CategoryScoreService service;

    @Test
    public void testOrder() {
        CategoryScore categoryScore = new CategoryScore(Category.PIZZA);
        CategoryScore save = categoryScoreRepository.save(categoryScore);

        Optional<CategoryScore> saved = categoryScoreRepository.findById(Category.PIZZA);
        Assert.assertTrue(saved.isPresent());
    }

    @Test(expected = CategoryScoreNotFoundException.class)
    public void testFindCategory404() {
        service.findById(Category.ARABIC);
    }

    @Test
    public void testFindCategoryById() {
        CategoryScore categoryScore = new CategoryScore(Category.PIZZA);
        CategoryScore save = categoryScoreRepository.save(categoryScore);

        CategoryScore saved = service.findById(Category.PIZZA);
        Assert.assertEquals(BigDecimal.ZERO, saved.totalRelevances());
    }
}
