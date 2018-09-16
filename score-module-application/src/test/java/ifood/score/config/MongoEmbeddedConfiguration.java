package ifood.score.config;

import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.mongo.tests.MongodForTestsFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@Configuration
@Profile({"test"})
public class MongoEmbeddedConfiguration {

    @Bean
    public MongoClient mongoClient() throws IOException {
        MongodForTestsFactory factory = MongodForTestsFactory.with(Version.Main.V3_6);
        return factory.newMongo();
    }
}
