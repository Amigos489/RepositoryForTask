package dao;

import entitys.BookEntity;
import exception.EntityNotFound;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

public class BookDao extends HibernateAbstractDao<BookEntity, Integer>{

    private final Logger log = LoggerFactory.getLogger(BookDao.class);
    private final Session session;

    public BookDao(Session session) {
        this.session = session;
    }

    @Override
    public List<BookEntity> findAll() {
         String hql = "FROM BookEntity";
         return session.createQuery(hql, BookEntity.class).getResultList();
    }

    public BookEntity findBookById(Integer id) {
        try {
            session.beginTransaction();
            BookEntity book = session.find(BookEntity.class, id);
            if (book == null) {
                throw new EntityNotFound(super.messageBookNotFound);
            }
            session.getTransaction().commit();
            return book;
        } catch (EntityNotFound e) {
            log.error(e.getMessage());
            session.getTransaction().rollback();
            return null;
        }
    }

    public void operationBookById(int id, boolean availability) throws EntityNotFound {
        try {
            session.beginTransaction();
            BookEntity book = session.find(BookEntity.class, id);
            if (book == null) {
                throw new EntityNotFound(super.messageBookNotFound);
            }
            book.setAvailability(availability);
            if (availability) {
                book.setDateAddWarehouse(LocalDate.now());
            }
            session.flush();
            session.update(book);
            session.getTransaction().commit();
        } catch (EntityNotFound e) {
            log.error(e.getMessage());
            session.getTransaction().rollback();
            throw e;
        }
    }

    public List<BookEntity> getStaleBook(Integer countMonthDefineStaleBook) {
        try {
            String hql = "FROM BookEntity b WHERE b.dateAddWarehouse < :dateForStaleBook";
            session.beginTransaction();
            LocalDate dateForStaleBook = LocalDate.now().minusMonths(countMonthDefineStaleBook);
            System.out.println(dateForStaleBook);
            List<BookEntity> staleBook = session.createQuery(hql, BookEntity.class).
                        setParameter("dateForStaleBook", dateForStaleBook).
                            getResultList();
            session.getTransaction().commit();
            return staleBook;
        } catch (Exception e) {
            log.error(e.getMessage());
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }
}
