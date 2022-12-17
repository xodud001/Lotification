package net.weather.monitor.consumer.config;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

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
