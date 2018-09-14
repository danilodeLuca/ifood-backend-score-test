package ifood.score.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class JmsConfiguration {

    @Value("${spring.activemq.broker-url}")
    private String url;

    @Value("${spring.activemq.user}")
    private String user;

    @Value("${spring.activemq.password}")
    private String password;

    @Value("${spring.activemq.packages.trust-all}")
    private Boolean trustAllPackages;

    @Bean
    public JmsTemplate jmsTemplate(ActiveMQConnectionFactory activeMQConnectionFactory) {
        return new JmsTemplate(activeMQConnectionFactory);
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(url);
        activeMQConnectionFactory.setUserName(user);
        activeMQConnectionFactory.setPassword(password);
        activeMQConnectionFactory.setTrustAllPackages(trustAllPackages);

        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setRedeliveryDelay(2000);
        redeliveryPolicy.setMaximumRedeliveries(5);
        redeliveryPolicy.setUseExponentialBackOff(true);

        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);

        activeMQConnectionFactory.setNonBlockingRedelivery(true);

        return activeMQConnectionFactory;
    }

}
