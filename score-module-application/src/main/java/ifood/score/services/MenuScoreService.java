package ifood.score.services;

import ifood.score.entities.MenuItemScore;
import ifood.score.exceptions.MenuItemScoreNotFoundException;
import ifood.score.repositories.MenuItemScoreRepository;
import ifood.score.repositories.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class MenuScoreService extends ScoreService<MenuItemScore> {

    @Autowired
    private MenuItemScoreRepository menuItemScoreRepository;

    public MenuItemScore findMenuItemScoreById(UUID menuId) {
        Optional<MenuItemScore> item = menuItemScoreRepository.findById(menuId);
        if (item.isPresent()) {
            return item.get();
        } else {
            throw new MenuItemScoreNotFoundException(menuId);
        }
    }

    @Override
    public ScoreRepository getRepository() {
        return menuItemScoreRepository;
    }
}
