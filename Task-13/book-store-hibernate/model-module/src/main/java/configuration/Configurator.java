package configuration;

import annotations.ConfigProperty;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

public class Configurator {
    private Class type;
    private Properties propertiesFile;
    private String nameFileProperties;

    public Configurator(String nameFileProperties) {
        this.nameFileProperties = nameFileProperties;
    }

    public Properties loadPropertyFile(String nameFile) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(new File(nameFile))) {
            Properties loadedProperties = new Properties();
            loadedProperties.load(fileInputStream);
            return loadedProperties;
        } catch (IOException e) {
            throw e;
        }
    }


    public Object configurationObject(Object object) {
        try {
            propertiesFile = loadPropertyFile(nameFileProperties);
            Class objectClass = object.getClass();
            for (Field field : objectClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(ConfigProperty.class)) {
                    ConfigProperty annotation = field.getAnnotation(ConfigProperty.class);
                    Class<?> type = annotation.type();

                    String key = objectClass.getSimpleName() + '.' + field.getName();
                    field.setAccessible(true);
                    if (type == Boolean.class) {
                        field.set(object, Boolean.valueOf(propertiesFile.getProperty(key)));
                    } else if (type == Integer.class) {
                        field.set(object, Integer.valueOf(propertiesFile.getProperty(key)));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Возникла ошибка при загрузке файла.");
        } catch (IllegalAccessException e) {
            System.out.println("Возникла ошибка при конфигурации объекта.");
        }
        return object;
    }
}
