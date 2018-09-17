package ifood.score;

import org.bson.types.Decimal128;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.math.BigDecimal;
import java.util.Arrays;

@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
@ComponentScan
@EnableJms
@EnableMongoRepositories
@EnableReactiveMongoRepositories
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class);
    }

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new Converter<BigDecimal, Decimal128>() {

                    @Override
                    public Decimal128 convert(@NonNull BigDecimal source) {
                        return new Decimal128(source);
                    }
                },
                new Converter<Decimal128, BigDecimal>() {

                    @Override
                    public BigDecimal convert(@NonNull Decimal128 source) {
                        return source.bigDecimalValue();
                    }

                }
        ));

    }
}
