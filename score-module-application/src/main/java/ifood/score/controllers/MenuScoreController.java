package ifood.score.controllers;

import ifood.score.entities.MenuItemScore;
import ifood.score.services.MenuScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
public class MenuScoreController {

    @Autowired
    private MenuScoreService menuScoreService;

    @GetMapping(value = "/menus/{menuId}/score", produces = MediaType.APPLICATION_JSON_VALUE)
    public MenuItemScore getScoresFromMenu(@PathVariable UUID menuId) {
        return menuScoreService.findMenuItemScoreById(menuId);
    }

    @GetMapping(value = "/menus/score/above/{relevance}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MenuItemScore> scoresAbove(@PathVariable BigDecimal relevance) {
        return menuScoreService.scoreAbove(relevance);
    }

    @GetMapping(value = "/menus/score/bellow/{relevance}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MenuItemScore> scoresBellow(@PathVariable BigDecimal relevance) {
        return menuScoreService.scoreBellow(relevance);
    }
}
