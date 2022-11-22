package net.weather.kakao.config;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@EnableConfigurationProperties({KakaoProperty.class})
@PropertySources({
        @PropertySource("classpath:env-${spring.profiles.active}.properties")
})
public class KakaoModuleConfig {

}
