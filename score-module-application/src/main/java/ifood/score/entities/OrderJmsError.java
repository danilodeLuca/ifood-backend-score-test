package ifood.score.entities;

import com.google.gson.Gson;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document
public class OrderJmsError {

    @Id
    private String id;

    private String messageSent;

    private String queueName;

    private String clazz;

    private Boolean critical;

    public OrderJmsError(String queueName, Object messageSent) {
        this.queueName = queueName;
        this.clazz = messageSent.getClass().getCanonicalName();
        if (messageSent instanceof UUID) {
            this.messageSent = messageSent.toString();
        } else {
            this.messageSent = new Gson().toJson(messageSent);
        }
        this.critical = Boolean.FALSE;
    }

    public Object getObject() throws ClassNotFoundException {
        return new Gson().fromJson(getMessageSent(), Class.forName(getClazz()));
    }
}
