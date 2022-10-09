package net.weather.monitor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestConsumer {

    private final static String TOPIC = "lotification";

    @KafkaListener(topics = TOPIC, groupId = "lotification")
    public void listen(String summoner){
        log.info("summoner={}", summoner);
    }
}
