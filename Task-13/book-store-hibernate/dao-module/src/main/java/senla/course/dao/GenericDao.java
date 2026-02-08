package senla.course.dao;

import java.io.Serializable;

public interface GenericDao<T, PK extends Serializable> {

    public PK save(T entity);

    public void update(T entity);

    public void delete(T entity);

    public T find(PK id);
}
