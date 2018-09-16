package ifood.score.exceptions;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class MenuItemScoreNotFoundException extends ScoreExceptions {

    public MenuItemScoreNotFoundException(UUID menuId) {
        super("Menu with Id " + menuId + " was not found!", HttpStatus.NOT_FOUND);
    }
}
