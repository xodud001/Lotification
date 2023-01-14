package net.weather.api.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@AllArgsConstructor
@ConfigurationProperties("token")
@Getter
public class JwtProperties {

    private String secretKey;
}