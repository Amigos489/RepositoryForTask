package senla.course.dao;

import senla.course.entitys.BookEntity;
import senla.course.entitys.RequestEntity;
import senla.course.exception.EntityNotFound;
import senla.course.exception.QueryInvalidResult;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.List;

@Repository
public class RequestDaoImpl extends HibernateAbstractDao<RequestEntity, Integer> implements IRequestDao {

    public RequestDaoImpl(Session session) {

        super(session, RequestEntity.class);
    }

    @Override
    public List<RequestEntity> findAll() {

        String hqlAllRequest = "SELECT r FROM RequestEntity r JOIN FETCH r.book";

        List<RequestEntity> requests = session.createQuery(hqlAllRequest, RequestEntity.class)
                .getResultList();

        for (RequestEntity request : requests) {

            request.setNameBook(request.getBook().getNameBook());
        }

        return requests;
    }

    @Override
    public Integer findActiveRequestOnBook(Integer bookId) {

        try {

            RequestEntity requests  = session.createQuery("FROM RequestEntity WHERE book.id = :bookId AND isClosed = false", RequestEntity.class)
                    .setParameter("bookId", bookId)
                        .getSingleResult();

            return requests.getRequestId();
        } catch (NoResultException e) {
            return -1;
        } catch (NonUniqueResultException e) {
            throw new QueryInvalidResult("Активных запросов на одну книгу не может быть > 1.");
        }
    }

    @Override
    public void createRequest(RequestEntity request) throws EntityNotFound {

        if (request.getBookId() == null || request.getBookId() <= 0) {
            throw new IllegalArgumentException(super.messageIncorrectId);
        }

        BookEntity book = session.find(BookEntity.class, request.getBookId());
        if (book == null) {
            throw new EntityNotFound(super.messageBookNotFound);
        }
        request.setBook(book);
        request.setNameBook(book.getNameBook());
        session.persist(request);
        session.flush();
    }

    @Override
    public void incrementRequestById(Integer requestId) throws EntityNotFound {

        if (requestId == null || requestId <= 0) {
            throw new IllegalArgumentException(super.messageIncorrectId);
        }

        RequestEntity request = session.get(RequestEntity.class, requestId);

        if (request == null) {
            throw new EntityNotFound(super.messageRequestNotFound);
        }

        request.incrementCountRequest();
        session.update(request);
        session.flush();
    }

    @Override
    public void closedRequestById(Integer requestId) throws EntityNotFound {
        RequestEntity request = session.get(RequestEntity.class, requestId);
        if (request == null) {
            throw new EntityNotFound(super.messageRequestNotFound);
        }
        request.setIsClosed(true);
        session.update(request);
        session.flush();
    }

    @Override
    public void closedRequestByBookId(Integer bookId) {
        String hql = "UPDATE RequestEntity r SET r.isClosed=true WHERE r.book.bookId=:bookId";

        if (bookId == null || bookId <= 0) {
            throw new IllegalArgumentException(super.messageIncorrectId);
        }

        session.createQuery(hql)
                .setParameter("bookId", bookId)
                    .executeUpdate();
    }
}
