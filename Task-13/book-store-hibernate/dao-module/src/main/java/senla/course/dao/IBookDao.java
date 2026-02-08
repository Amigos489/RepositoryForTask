package senla.course.dao;

import senla.course.entitys.BookEntity;
import senla.course.exception.EntityListEmpty;
import senla.course.exception.EntityNotFound;

import java.util.List;

public interface IBookDao {

    BookEntity findBookById(Integer id) throws EntityNotFound;

    void operationBookById(int id, boolean availability) throws EntityNotFound;

    List<BookEntity> getStaleBook(Integer countMonthDefineStaleBook) throws EntityListEmpty;

    List<BookEntity> findAll() throws EntityListEmpty;
}
