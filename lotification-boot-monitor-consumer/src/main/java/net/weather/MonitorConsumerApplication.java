package net.weather;


import net.weather.monitor.consumer.config.KafkaConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableConfigurationProperties({KafkaConfig.class})
@SpringBootApplication(scanBasePackages = {"net.weather"})
public class MonitorConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorConsumerApplication.class);
    }
}
