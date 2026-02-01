package dao;

import entitys.BookEntity;
import entitys.RequestEntity;
import exception.EntityNotFound;
import exception.QueryInvalidResult;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.List;

public class RequestDao extends HibernateAbstractDao<RequestEntity, Integer>{

    private final Logger log = LoggerFactory.getLogger(RequestDao.class);
    private final Session session;

    public RequestDao(Session session) {
        this.session = session;
    }

    @Override
    public List<RequestEntity> findAll() {

        String hqlAllRequest = "SELECT r FROM RequestEntity r";

        String hqlAllBook = "SELECT r FROM BookEntity r";

        List<RequestEntity> requests = session.createQuery(hqlAllRequest, RequestEntity.class)
                .getResultList();
        List<BookEntity> books = session.createQuery(hqlAllBook, BookEntity.class)
                .getResultList();

        for (RequestEntity request : requests) {

            for (BookEntity book : books) {

                if (request.getBook().getBookId().equals(book.getBookId())) {
                    request.setNameBook(book.getNameBook());;
                }
            }
        }

        return requests;
    }

    public Integer findActiveRequestOnBook(Integer bookId) {

        try {

            RequestEntity requests  = session.createQuery("FROM RequestEntity WHERE book.id = :bookId AND isClosed = false", RequestEntity.class)
                    .setParameter("bookId", bookId)
                        .getSingleResult();

            return requests.getRequestId();
        } catch (NoResultException e) {

            return -1;
        } catch (NonUniqueResultException e) {
            log.error("Активных запросов на одну книгу не может быть > 1.");
            throw new QueryInvalidResult("Активных запросов на одну книгу не может быть > 1.");
        }
    }

    public void createRequest(RequestEntity request) throws EntityNotFound {

        try {

            session.beginTransaction();
            BookEntity book = session.find(BookEntity.class, request.getBookId());
            if (book == null) {
                throw new EntityNotFound(super.messageBookNotFound);
            }
            request.setBook(book);
            request.setNameBook(book.getNameBook());
            session.save(request);
            session.getTransaction().commit();

        } catch (EntityNotFound e) {
            log.error(e.getMessage());
            session.getTransaction().rollback();
            throw e;
        }
    }

    public void incrementRequestById(Integer requestId) {
        try {

            session.beginTransaction();
            RequestEntity request = session.get(RequestEntity.class, requestId);

            if (request == null) {
                throw new EntityNotFound(super.messageRequestNotFound);
            }

            request.incrementCountRequest();
            session.update(request);
            session.flush();
            session.getTransaction().commit();
        } catch (EntityNotFound e) {
            log.error(e.getMessage());
            session.getTransaction().rollback();
        }
    }

    public void closedRequestById(Integer requestId) throws EntityNotFound {
        RequestEntity request = session.get(RequestEntity.class, requestId);
        if (request == null) {
            throw new EntityNotFound(super.messageRequestNotFound);
        }
        request.setIsClosed(false);
        session.update(request);
    }

    public void closedRequestByBookId(Integer bookId) {
        try {
            String hql = "UPDATE RequestEntity r SET r.isClosed=true WHERE r.book.bookId=:bookId";

            session.beginTransaction();

            session.createQuery(hql)
                        .setParameter("bookId", bookId)
                            .executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            session.getTransaction().rollback();
        }
    }


}
