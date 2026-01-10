import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DI {
    private Map<Class, Object> singletonInstances = new HashMap<>();
    private static DI instance;

    private DI() {}
    public <T> void registerBean(Class<T> beanType, T bean) {
        singletonInstances.put(beanType, bean);
    }

    public <T> T getBean(Class<T> beanType) {
        return (T) singletonInstances.get(beanType);
    }

    public synchronized static DI getInstance() {
        if(instance == null) {
            instance = new DI();
        }
        return instance;
    }

    public void injectDependencies(Object obj) throws SecurityException {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent((Class<? extends Annotation>) Inject.class)) {
                Class<?> fieldType = field.getType();
                Object dependency = singletonInstances.get(fieldType);
                if (dependency != null) {
                    field.setAccessible(true);
                    try {
                        field.set(obj, dependency);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
