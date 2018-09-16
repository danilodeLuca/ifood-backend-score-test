package ifood.score.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Document
@Data
public class RelevanceOrder {

    @Id
    private UUID orderId;

    private Date confirmationDate;

    private RelevanceStatus status;

    private List<RelevanceOrderItem> relevances;

    public enum RelevanceStatus {
        DONE, CANCELLED, EXPIRED;
    }
}
