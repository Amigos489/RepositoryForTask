package dao;

import exception.EntityListEmpty;
import util.HibernateUtil;

import java.io.Serializable;
import java.util.List;

public abstract class HibernateAbstractDao<T, PK extends Serializable> implements GenericDao<T, PK> {
    private Class<T> type;
    protected final String messageBookNotFound = "Книга с указанным id не найден.";
    protected final String messageOrderNotFound = "Заказ с указанным id не найден.";
    protected final String messageRequestNotFound = "Запрос с указанным id не найден.";
    protected final String messageEntityListEmpty = "Список пустой.";
    protected final String messageIncorrectId = "Передан некорректный id";

    @Override
    public PK save(T entity) {
        return (PK) HibernateUtil.getCurrentSession().save(entity);
    }

    @Override
    public void update(T entity) {
        HibernateUtil.getCurrentSession().update(entity);
    }

    @Override
    public void delete(PK id) {
        HibernateUtil.getCurrentSession().delete(id);
    }

    public List<T> findAll() throws EntityListEmpty {
        return HibernateUtil.getCurrentSession().createCriteria(type).list();
    }

    @Override
    public T find(PK id) {
        return HibernateUtil.getCurrentSession().load(type, id);
    }
}
