package senla.course.bank.producer.config;

import org.springframework.context.annotation.*;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("senla.course.bank.producer")
@EnableKafka
@EnableScheduling
@EnableTransactionManagement
public class AppConfig {
}