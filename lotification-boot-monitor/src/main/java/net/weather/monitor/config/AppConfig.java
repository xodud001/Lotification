package net.weather.monitor.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Slf4j
@Configuration
@PropertySources({
        @PropertySource("classpath:env-${spring.profiles.active}.properties")
})
@RequiredArgsConstructor
public class AppConfig {

    private final Environment environment;
    private final DataSource dataSource;

    private final KafkaConfig config;

    @EventListener(ApplicationReadyEvent.class)
    public void ready() throws Exception {
        log.info("database url={}", dataSource.getConnection().getMetaData().getURL());
        log.info("database url={}", dataSource.getConnection().getMetaData().getUserName());

        log.info("env.bootstrap-address={}", environment.getProperty("env.bootstrap-address"));
        log.info("env.message-topic-name={}", environment.getProperty("env.message-topic-name"));
        log.info("env.greeting-topic-name={}", environment.getProperty("env.greeting-topic-name"));
        log.info("env.filtered-topic-name={}", environment.getProperty("env.filtered-topic-name"));
        log.info("env.partitioned-topic-name={}", environment.getProperty("env.partitioned-topic-name"));
        log.info("env.riot-api-key={}", environment.getProperty("env.riot-api-key"));

        log.info("kafka-config Bootstrap Address={}", config.getBootstrapAddress());
        log.info("kafka-config Message Topic Name={}", config.getMessageTopicName());
    }
}
