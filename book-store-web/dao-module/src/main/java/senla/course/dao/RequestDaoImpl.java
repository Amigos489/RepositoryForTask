package senla.course.dao;

import org.hibernate.SessionFactory;
import senla.course.entitys.BookEntity;
import senla.course.entitys.RequestEntity;
import senla.course.exception.QueryInvalidResult;
import org.springframework.stereotype.Repository;

import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import java.util.List;

@Repository
public class RequestDaoImpl extends HibernateAbstractDao<RequestEntity, Integer> implements IRequestDao {

    public RequestDaoImpl(SessionFactory sessionFactory) {

        super(sessionFactory, RequestEntity.class);
    }

    @Override
    public List<RequestEntity> findAll(String criteria) {

        String hqlAllRequest;

        switch (criteria) {
            case "countrequest": {
                hqlAllRequest = "SELECT r FROM RequestEntity r JOIN FETCH r.book ORDER BY r.countRequest";
            }
            case "namebook": {
                hqlAllRequest = "SELECT r FROM RequestEntity r JOIN FETCH r.book ORDER BY r.book.nameBook";
            }
            default: {
                hqlAllRequest = "SELECT r FROM RequestEntity r JOIN FETCH r.book";
            }

        }

        List<RequestEntity> requests = sessionFactory.getCurrentSession().createQuery(hqlAllRequest, RequestEntity.class)
                .getResultList();

        for (RequestEntity request : requests) {

            request.setNameBook(request.getBook().getNameBook());
        }

        return requests;
    }

    @Override
    public Integer findActiveRequestOnBook(Integer bookId) {
        try {

            RequestEntity requests  = sessionFactory.getCurrentSession().createQuery("FROM RequestEntity WHERE book.id = :bookId AND isClosed = false", RequestEntity.class)
                    .setParameter("bookId", bookId)
                        .getSingleResult();

            System.out.println(requests.getRequestId());
            return requests.getRequestId();
        } catch (NoResultException e) {
            return -1;
        } catch (NonUniqueResultException e) {
            throw new QueryInvalidResult("Активных запросов на одну книгу не может быть > 1.");
        }
    }

    @Override
    public void createRequest(RequestEntity request) {
        BookEntity book = sessionFactory.getCurrentSession().find(BookEntity.class, request.getBookId());
        request.setBook(book);
        request.setNameBook(book.getNameBook());
        sessionFactory.getCurrentSession().persist(request);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public void incrementRequestById(Integer requestId) {
        RequestEntity request = sessionFactory.getCurrentSession().get(RequestEntity.class, requestId);
        request.incrementCountRequest();
        sessionFactory.getCurrentSession().update(request);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public void closedRequestById(Integer requestId) {
        RequestEntity request = sessionFactory.getCurrentSession().get(RequestEntity.class, requestId);
        request.setIsClosed(true);
        sessionFactory.getCurrentSession().update(request);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public void closedRequestByBookId(Integer bookId) {

        String hql = "UPDATE RequestEntity r SET r.isClosed=true WHERE r.book.bookId=:bookId";
        sessionFactory.getCurrentSession().createQuery(hql)
                .setParameter("bookId", bookId)
                    .executeUpdate();
    }
}
