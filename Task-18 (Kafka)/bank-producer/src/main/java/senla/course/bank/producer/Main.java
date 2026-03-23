package senla.course.bank.producer;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import senla.course.bank.producer.config.AppConfig;

public class Main {
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(AppConfig.class);
    }
}