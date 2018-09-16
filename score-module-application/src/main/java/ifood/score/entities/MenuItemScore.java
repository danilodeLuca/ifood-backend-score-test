package ifood.score.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuItemScore extends Score<UUID> {

    public MenuItemScore(UUID id) {
        super(id);
    }
}
