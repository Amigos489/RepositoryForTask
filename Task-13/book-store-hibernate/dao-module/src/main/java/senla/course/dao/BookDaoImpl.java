package senla.course.dao;

import senla.course.entitys.BookEntity;
import senla.course.exception.EntityListEmpty;
import senla.course.exception.EntityNotFound;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class BookDaoImpl extends HibernateAbstractDao<BookEntity, Integer> implements IBookDao {

    public BookDaoImpl(Session session) {
        super(session, BookEntity.class);
    }

    @Override
    public List<BookEntity> findAll() throws EntityListEmpty {
        try {
            String hql = "FROM BookEntity";
            List<BookEntity> books = session.createQuery(hql, BookEntity.class).getResultList();
            if (books.isEmpty()) {
                throw new EntityListEmpty(super.messageEntityListEmpty);
            }
            return books;
        } catch (EntityListEmpty e) {
            throw e;
        }
    }

    //Найти книгу по id
    public BookEntity findBookById(Integer id) throws EntityNotFound {
        if (id == null || id <= 0) {
            throw new  IllegalArgumentException(super.messageIncorrectId);
        }
        BookEntity book = session.find(BookEntity.class, id);
        if (book == null) {
            throw new EntityNotFound(super.messageBookNotFound);
        }
        return book;
    }

    @Override
    public void operationBookById(int id, boolean availability) throws EntityNotFound {
        BookEntity book = session.find(BookEntity.class, id);
        if (book == null) {
            throw new EntityNotFound(super.messageBookNotFound);
        }
        book.setAvailability(availability);
        if (availability) {
            book.setDateAddWarehouse(LocalDate.now());
        }
        session.update(book);
        session.flush();
    }

    @Override
    public List<BookEntity> getStaleBook(Integer countMonthDefineStaleBook) throws EntityListEmpty {
        String hqlGetStaleBook = "FROM BookEntity b WHERE b.dateAddWarehouse < :dateForStaleBook";
        LocalDate dateForStaleBook = LocalDate.now().minusMonths(countMonthDefineStaleBook);
        List<BookEntity> staleBook = session.createQuery(hqlGetStaleBook, BookEntity.class).
                setParameter("dateForStaleBook", dateForStaleBook).
                    getResultList();
        if (staleBook.isEmpty()) {
            throw new EntityListEmpty(super.messageEntityListEmpty);
        }
        return staleBook;
    }
}
