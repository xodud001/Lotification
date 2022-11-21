package net.weather.api.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
@Configuration
@PropertySources({
        @PropertySource("classpath:env-${spring.profiles.active}.properties")
})
@EnableConfigurationProperties({JwtProperties.class})
@RequiredArgsConstructor
public class WebConfig {

    private final Environment environment;
    private final DataSource dataSource;

    @EventListener(ApplicationReadyEvent.class)
    public void ready() throws Exception {
        log.info("database url={}", dataSource.getConnection().getMetaData().getURL());
        log.info("database url={}", dataSource.getConnection().getMetaData().getUserName());

        log.info("env.grant-type={}", environment.getProperty("kakao.grant-type"));
        log.info("kakao.grant-type={}", environment.getProperty("kakao.api-key"));
        log.info("kakao.grant-type={}", environment.getProperty("kakao.redirect-url"));
        log.info("kakao.grant-type={}", environment.getProperty("token.secret-key"));
        log.info("kakao.grant-type={}", environment.getProperty("riot.api-key"));
    }
}
