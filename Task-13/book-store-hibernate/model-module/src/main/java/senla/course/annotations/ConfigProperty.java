package senla.course.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ConfigProperty {
    String confingFileName() default "src/main/resources/config.properties";
    String propertyName() default "";
    Class<?> type() default String.class;
}
