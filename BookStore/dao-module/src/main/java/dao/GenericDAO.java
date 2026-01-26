package dao;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T> {
    T getByID(int id);
    List<T> getAll();

    void save(T entity) throws SQLException;
    void update(T entity) throws SQLException;

    void delete(T entity);
    void deleteByID(int id);
}