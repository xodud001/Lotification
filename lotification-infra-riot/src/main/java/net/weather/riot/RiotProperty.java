package net.weather.riot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("riot")
@AllArgsConstructor
@Getter
public class RiotProperty {

    private String apiKey;

}