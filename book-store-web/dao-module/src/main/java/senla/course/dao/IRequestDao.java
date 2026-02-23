package senla.course.dao;

import senla.course.entitys.RequestEntity;
import senla.course.exception.EntityListEmpty;
import senla.course.exception.EntityNotFound;

import java.util.List;

public interface IRequestDao {

    Integer findActiveRequestOnBook(Integer bookId);

    void createRequest(RequestEntity request);

    void incrementRequestById(Integer requestId);

    void closedRequestById(Integer requestId);

    void closedRequestByBookId(Integer bookId);

    List<RequestEntity> findAll(String criteria);
}
