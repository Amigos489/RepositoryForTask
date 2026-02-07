package dao;

import entitys.BookEntity;
import entitys.RequestEntity;
import exception.EntityListEmpty;
import exception.EntityNotFound;

import java.util.List;

public interface IRequestDao {

    Integer findActiveRequestOnBook(Integer bookId);

    void createRequest(RequestEntity request) throws EntityNotFound;

    void incrementRequestById(Integer requestId) throws EntityNotFound;

    void closedRequestById(Integer requestId) throws EntityNotFound;

    void closedRequestByBookId(Integer bookId);

    List<RequestEntity> findAll() throws EntityListEmpty;
}
