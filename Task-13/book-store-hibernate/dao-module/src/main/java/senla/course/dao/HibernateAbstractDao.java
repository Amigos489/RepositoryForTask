package dao;

import exception.EntityListEmpty;
import org.hibernate.Session;
import util.HibernateUtil;

import java.io.Serializable;
import java.util.List;

public abstract class HibernateAbstractDao<T, PK extends Serializable> implements GenericDao<T, PK> {
    private Class<T> type;
    protected Session session;
    protected final String messageBookNotFound = "Книга с указанным id не найден.";
    protected final String messageOrderNotFound = "Заказ с указанным id не найден.";
    protected final String messageRequestNotFound = "Запрос с указанным id не найден.";
    protected final String messageEntityListEmpty = "Список пустой.";
    protected final String messageIncorrectId = "Передан некорректный id";

    public HibernateAbstractDao(Session session, Class<T> type) {
        this.session = session;
        this.type = type;
    }

    @Override
    public PK save(T entity) {
        return (PK) session.save(entity);
    }

    @Override
    public void update(T entity) {
        session.update(entity);
    }

    @Override
    public void delete(T entity) {
        session.delete(entity);
    }

    public List<T> findAll() throws EntityListEmpty {
        return session.createCriteria(type).list();
    }

    @Override
    public T find(PK id) {
        return session.find(type, id);
    }
}
