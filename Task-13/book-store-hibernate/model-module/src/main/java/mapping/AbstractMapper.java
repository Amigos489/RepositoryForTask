package mapping;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMapper<T, E> {

    public abstract T entityToModelMapping(E entityForMapping);

    public abstract E modelToEntityMapping(T modelForMapping);

    public List<T> entityListToModelListMapping(List<E> listEntityFoMapping) {
        List<T> entitys = new ArrayList<>();
        for (E mappingEntity : listEntityFoMapping) {
            T entity = entityToModelMapping(mappingEntity);
            entitys.add(entity);
        }
        return entitys;
    }
}
