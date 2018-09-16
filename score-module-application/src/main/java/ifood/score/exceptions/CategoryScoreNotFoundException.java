package ifood.score.exceptions;

import ifood.score.menu.Category;
import org.springframework.http.HttpStatus;

public class CategoryScoreNotFoundException extends ScoreExceptions {
    public CategoryScoreNotFoundException(Category category) {
        super("Category with Id " + category + " was not found!", HttpStatus.NOT_FOUND);

    }
}
