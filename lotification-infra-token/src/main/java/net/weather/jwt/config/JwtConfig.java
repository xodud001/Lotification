package net.weather.jwt.config;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("classpath:env-${spring.profiles.active}.properties")
})
@EnableConfigurationProperties({JwtProperties.class})
public class JwtConfig {

}
