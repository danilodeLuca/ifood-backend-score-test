package ifood.score.services;

import ifood.score.entities.CategoryScore;
import ifood.score.entities.MenuItemScore;
import ifood.score.exceptions.CategoryScoreNotFoundException;
import ifood.score.exceptions.MenuItemScoreNotFoundException;
import ifood.score.menu.Category;
import ifood.score.repositories.CategoryScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryScoreService {

    @Autowired
    private CategoryScoreRepository repository;

    public CategoryScore findById(Category category) {
        Optional<CategoryScore> categorySaved = repository.findById(category);
        if (categorySaved.isPresent()) {
            return categorySaved.get();
        } else {
            throw new CategoryScoreNotFoundException(category);
        }
    }
}
