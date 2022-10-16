package net.weather.jwt.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@AllArgsConstructor
@ConfigurationProperties("token")
@Getter
public class JwtProperties {

    private String secretKey;
}
