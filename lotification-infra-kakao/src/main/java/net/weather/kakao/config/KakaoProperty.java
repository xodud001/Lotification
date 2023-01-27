package net.weather.kakao.config;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@AllArgsConstructor
@ConfigurationProperties("kakao")
@Getter
public class KakaoProperty {

    private String grantType;
    private String apiKey;
    private String redirectUrl;
}
