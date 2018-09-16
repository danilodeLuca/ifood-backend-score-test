package ifood.score.entities;


import ifood.score.menu.Category;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryScore extends Score<Category> {

    public CategoryScore(Category id) {
        super(id);
    }
}
