package senla.course.bank.consumer.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("senla.course.bank.consumer")
@EnableKafka
@EnableTransactionManagement
public class AppConfig {
}
