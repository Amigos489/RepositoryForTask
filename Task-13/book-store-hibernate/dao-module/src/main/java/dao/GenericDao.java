package dao;

import exception.EntityListEmpty;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, PK extends Serializable> {

    public PK save(T entity);

    public void update(T entity);

    public void delete(PK id);

    public T find(PK id);
}
