package net.weather.api;

import lombok.RequiredArgsConstructor;
import net.weather.riot.RiotProperty;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final Environment environment;
    private final RiotProperty property;

    @GetMapping("/riot-config")
    public Map<String, String> getProperty() {
        Map<String, String> map = new HashMap<>();

        map.put("riot.api-key-env", environment.getProperty("riot.api-key"));
        map.put("riot.api-key-class", property.getApiKey());

        return map;
    }

}
