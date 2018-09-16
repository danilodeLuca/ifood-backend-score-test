package ifood.score.services;

import ifood.score.config.BaseTest;
import ifood.score.entities.CategoryScore;
import ifood.score.entities.MenuItemScore;
import ifood.score.exceptions.MenuItemScoreNotFoundException;
import ifood.score.menu.Category;
import ifood.score.repositories.MenuItemScoreRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.UUID;

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
        Assert.assertEquals(BigDecimal.ZERO, menuItemScoreById.getTotalRelevances());
    }

}
