package ifood.score.services;

import ifood.score.entities.CategoryScore;
import ifood.score.exceptions.CategoryScoreNotFoundException;
import ifood.score.menu.Category;
import ifood.score.repositories.CategoryScoreRepository;
import ifood.score.repositories.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryScoreService extends ScoreService<CategoryScore> {

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

    @Override
    public ScoreRepository getRepository() {
        return repository;
    }
}
