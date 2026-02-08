package dao;

import exception.EntityListEmpty;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, PK extends Serializable> {

    public PK save(T entity);

    public void update(T entity);

    public void delete(T entity);

    public T find(PK id);
}
