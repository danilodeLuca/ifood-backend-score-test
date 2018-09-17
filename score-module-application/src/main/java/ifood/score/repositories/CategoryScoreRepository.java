package ifood.score.repositories;

import ifood.score.entities.CategoryScore;
import ifood.score.menu.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryScoreRepository extends ScoreRepository<CategoryScore, Category> {
}
