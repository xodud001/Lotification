package net.weather.monitor.producer.config;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@ConstructorBinding
@ConfigurationProperties("env")
@AllArgsConstructor
@Getter
public class KafkaConfig {

    private String bootstrapAddress;

    private String messageTopicName;

//    @Bean
//    public KafkaAdmin kafkaAdmin(){
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//        return new KafkaAdmin(configs);
//    }
//
//    @Bean
//    public NewTopic topic1(){
//        return new NewTopic(messageTopicName, 1, (short) 1);
//    }

}
