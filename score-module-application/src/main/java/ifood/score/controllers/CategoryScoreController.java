package ifood.score.controllers;

import ifood.score.entities.CategoryScore;
import ifood.score.menu.Category;
import ifood.score.services.CategoryScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryScoreController {

    @Autowired
    private CategoryScoreService categoryScoreService;

    @GetMapping(value = "/categories/{menuId}/score", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryScore getScoresFromMenu(@PathVariable Category category) {
        return categoryScoreService.findById(category);
    }
}
