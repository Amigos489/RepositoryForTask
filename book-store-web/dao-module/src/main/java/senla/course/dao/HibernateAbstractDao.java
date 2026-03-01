package senla.course.dao;

import org.hibernate.SessionFactory;
import senla.course.exception.EntityListEmpty;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

public abstract class HibernateAbstractDao<T, PK extends Serializable> implements GenericDao<T, PK> {
    private Class<T> type;
    protected SessionFactory sessionFactory;
    protected final String messageIncorrectId = "Передан некорректный id";

    public HibernateAbstractDao(SessionFactory sessionFactory, Class<T> type) {
        this.sessionFactory = sessionFactory;
        this.type = type;
    }

    @Override
    public PK save(T entity) {
        return (PK) sessionFactory.getCurrentSession().save(entity);
    }

    @Override
    public void update(T entity) {
        sessionFactory.getCurrentSession().update(entity);
    }

    @Override
    public void delete(T entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    public List<T> findAll(String criteria) {
        return sessionFactory.getCurrentSession()
                .createQuery("from " + type.getSimpleName(), type)
                .getResultList();
    }

    @Override
    public T find(PK id) {
        return sessionFactory.getCurrentSession().find(type, id);
    }
}
