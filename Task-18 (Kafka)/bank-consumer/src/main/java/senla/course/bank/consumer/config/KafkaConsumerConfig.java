package senla.course.bank.consumer.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import senla.course.bank.common.dto.TransferDto;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, TransferDto> consumerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "kafka-1:9092,kafka-2:9092,kafka-3:9092");

        props.put(ConsumerConfig.GROUP_ID_CONFIG, "bank-group");

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);

        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        props.put(ConsumerConfig.CLIENT_ID_CONFIG,
                System.getenv().getOrDefault("CLIENT_ID", "default-client"));

        props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");

        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 50);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new JsonDeserializer<>(TransferDto.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TransferDto> kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, TransferDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());

        factory.setBatchListener(true);
        factory.getContainerProperties()
                .setAckMode(ContainerProperties.AckMode.BATCH);
        return factory;
    }
}
