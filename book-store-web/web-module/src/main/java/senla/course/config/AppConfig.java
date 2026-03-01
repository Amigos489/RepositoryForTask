package senla.course.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"senla.course"})
@PropertySource("classpath:config.properties")
public class AppConfig {
}