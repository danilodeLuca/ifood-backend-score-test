package ifood.score.controllers;

import ifood.score.entities.CategoryScore;
import ifood.score.entities.MenuItemScore;
import ifood.score.menu.Category;
import ifood.score.services.CategoryScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class CategoryScoreController {

    @Autowired
    private CategoryScoreService categoryScoreService;

    @GetMapping(value = "/categories/{category}/score", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryScore getScoresFromMenu(@PathVariable Category category) {
        return categoryScoreService.findById(category);
    }

    @GetMapping(value = "/categories/score/above/{relevance}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoryScore> scoresAbove(@PathVariable BigDecimal relevance) {
        return categoryScoreService.scoreAbove(relevance);
    }

    @GetMapping(value = "/categories/score/bellow/{relevance}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoryScore> scoresBellow(@PathVariable BigDecimal relevance) {
        return categoryScoreService.scoreBellow(relevance);
    }

    public static void main(String[] args) {
        System.out.println("2".compareTo("19.000"));
    }
}
