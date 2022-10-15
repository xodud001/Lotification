package net.weather.config;

import net.weather.config.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("classpath:env.properties")
})
@EnableConfigurationProperties({JwtProperties.class})
public class WebConfig {
}
