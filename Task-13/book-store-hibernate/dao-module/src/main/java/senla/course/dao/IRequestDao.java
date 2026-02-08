package senla.course.dao;

import senla.course.entitys.RequestEntity;
import senla.course.exception.EntityListEmpty;
import senla.course.exception.EntityNotFound;

import java.util.List;

public interface IRequestDao {

    Integer findActiveRequestOnBook(Integer bookId);

    void createRequest(RequestEntity request) throws EntityNotFound;

    void incrementRequestById(Integer requestId) throws EntityNotFound;

    void closedRequestById(Integer requestId) throws EntityNotFound;

    void closedRequestByBookId(Integer bookId);

    List<RequestEntity> findAll() throws EntityListEmpty;
}
