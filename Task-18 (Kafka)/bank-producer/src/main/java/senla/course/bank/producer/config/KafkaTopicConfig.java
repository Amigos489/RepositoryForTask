package senla.course.bank.producer.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(
                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
                "kafka-1:9092,kafka-2:9092,kafka-3:9092"
        );
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic bankTopic() {
        return TopicBuilder.name("bank-topic")
                .partitions(3)
                .replicas(3)
                .configs(Map.of(
                        "retention.ms", "300000",
                        "min.insync.replicas", "2"
                ))
                .build();
    }
}
