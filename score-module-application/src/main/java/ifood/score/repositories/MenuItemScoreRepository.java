package ifood.score.repositories;

import ifood.score.entities.MenuItemScore;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MenuItemScoreRepository extends ScoreRepository<MenuItemScore, UUID> {
}
