package senla.course.bank.consumer;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import senla.course.bank.consumer.config.AppConfig;

public class Main {
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(AppConfig.class);
    }
}