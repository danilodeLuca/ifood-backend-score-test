package ifood.score.services;

import ifood.score.config.BaseTest;
import ifood.score.entities.MenuItemScore;
import ifood.score.exceptions.MenuItemScoreNotFoundException;
import ifood.score.repositories.MenuItemScoreRepository;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static ifood.score.utils.MathUtils.scale;

public class MenuScoreServiceTest extends BaseTest {

    @Autowired
    private MenuScoreService service;
    @Autowired
    private MenuItemScoreRepository repository;

    @Test(expected = MenuItemScoreNotFoundException.class)
    public void findMenuItemById404() {
        service.findMenuItemScoreById(UUID.randomUUID());
    }

    @Test
    public void findMenuItemById() {
        UUID id = UUID.randomUUID();

        MenuItemScore menu = new MenuItemScore(id);
        menu = repository.save(menu);

        MenuItemScore menuItemScoreById = service.findMenuItemScoreById(id);
        Assert.assertEquals(scale(BigDecimal.ZERO), menuItemScoreById.totalRelevances());
    }

    @Test
    @Ignore //With embedded mongo it does not work well
    public void testFindAbove() {
        UUID id = UUID.randomUUID();
        MenuItemScore menu = new MenuItemScore(id);
        menu.setRelevance(BigDecimal.valueOf(100));
        menu = repository.save(menu);

        MenuItemScore menuItemScoreById = service.findMenuItemScoreById(id);
        Assert.assertEquals(BigDecimal.valueOf(100), menuItemScoreById.getRelevance());

        List<MenuItemScore> menuItemScores = service.scoreAbove(BigDecimal.valueOf(40), Pageable.unpaged());
        Assert.assertEquals(1, menuItemScores.size());
    }

}
