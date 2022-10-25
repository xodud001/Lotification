package net.weather.riot;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@EnableConfigurationProperties({RiotProperty.class})
@PropertySources({
        @PropertySource("classpath:env.properties")
})
public class RiotModuleConfig {
}
