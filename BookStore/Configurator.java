import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public class Configurator {

    public static void configure(Object obj) {
        Class<?> clazz = obj.getClass();
        Properties props = load("config.property");

        for (Field field : clazz.getDeclaredFields()) {

            if (!field.isAnnotationPresent(ConfigProperty.class)) {
                continue;
            }

            String key = clazz.getSimpleName() + "." + field.getName();
            String value = props.getProperty(key);

            field.setAccessible(true);

            try {
                if (field.getType() == int.class) {
                    field.setInt(obj, Integer.parseInt(value));
                } else if (field.getType() == boolean.class) {
                    field.setBoolean(obj, Boolean.parseBoolean(value));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static Properties load(String file) {
        Properties props = new Properties();
        try (InputStream in = new FileInputStream(file)) {
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return props;
    }
}
