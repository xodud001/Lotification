package net.weather.monitor;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final static String TOPIC = "lotification";

//    @Scheduled(fixedRate = 3000)
    public void produce(){
        kafkaTemplate.send(TOPIC, "롤로노아 김동영");
        log.info("send message");
    }
}
