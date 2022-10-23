package net.weather.api.session;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionStore {

    private final Map<String, Authentication> session = new ConcurrentHashMap<>();


}
