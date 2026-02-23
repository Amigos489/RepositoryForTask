package senla.course.dao;

import senla.course.entitys.BookEntity;
import senla.course.exception.EntityListEmpty;
import senla.course.exception.EntityNotFound;

import java.util.List;

public interface IBookDao {

    BookEntity findBookById(Integer id);

    void operationBookById(int id, boolean availability);

    List<BookEntity> getStaleBook(Integer countMonthDefineStaleBook, String criteria);

    List<BookEntity> findAll(String criteria);
}
