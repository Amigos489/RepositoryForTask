package senla.course.dao;

import org.hibernate.SessionFactory;
import senla.course.entitys.BookEntity;
import senla.course.exception.EntityListEmpty;
import senla.course.exception.EntityNotFound;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class BookDaoImpl extends HibernateAbstractDao<BookEntity, Integer> implements IBookDao {

    public BookDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, BookEntity.class);
    }

    //Найти книгу по id
    public BookEntity findBookById(Integer id) {
        return sessionFactory.getCurrentSession().find(BookEntity.class, id);
    }

    @Override
    public List<BookEntity> findAll(String criteria) {
        String hql;

        switch (criteria) {
            case "namebook": {
                hql = "FROM BookEntity ORDER BY nameBook";
                break;
            }
            case "datepublication" : {
                hql = "FROM BookEntity ORDER BY datePublication";
                break;
            }
            case "price" : {
                hql = "FROM BookEntity ORDER BY price";
                break;
            }
            case "availability" : {
                hql = "FROM BookEntity ORDER BY availability";
                break;
            }
            default:{
                hql = "FROM BookEntity";
            };
        }

        List<BookEntity> books = sessionFactory.getCurrentSession().createQuery(hql, BookEntity.class).getResultList();
        return books;
    }

    @Override
    public void operationBookById(int id, boolean availability) {
        BookEntity book = sessionFactory.getCurrentSession().find(BookEntity.class, id);
        book.setAvailability(availability);
        if (availability) {
            book.setDateAddWarehouse(LocalDate.now());
        }
        sessionFactory.getCurrentSession().update(book);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public List<BookEntity> getStaleBook(Integer countMonthDefineStaleBook, String criteria) {

        String hqlGetStaleBook;

        switch (criteria) {
            case "dateaddwarehouse": {
                hqlGetStaleBook = "FROM BookEntity b WHERE b.dateAddWarehouse < :dateForStaleBook ORDER BY dateAddWarehouse";
                break;
            }
            case "price": {
                hqlGetStaleBook = "FROM BookEntity b WHERE b.dateAddWarehouse < :dateForStaleBook ORDER BY price";
                break;
            }
            default: {
                hqlGetStaleBook = "FROM BookEntity b WHERE b.dateAddWarehouse < :dateForStaleBook";
            }
        }

        LocalDate dateForStaleBook = LocalDate.now().minusMonths(countMonthDefineStaleBook);

        return sessionFactory.getCurrentSession().createQuery(hqlGetStaleBook, BookEntity.class).
                setParameter("dateForStaleBook", dateForStaleBook).
                    getResultList();
    }
}
