package senla.course.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import senla.course.dao.DaoManager;
import senla.course.dto.BookDto;
import senla.course.entitys.BookEntity;
import senla.course.exception.BookNotFoundException;
import senla.course.exception.IncorrectIdException;
import senla.course.exception.ListBookEmptyException;
import senla.course.mapper.Mapper;

import java.util.List;

@Service
public class BookService {

    @Value("${warehouse.countMonthDefineStaleBook}")
    private int countMonthDefineStaleBook;
    private DaoManager daoManager;
    private Mapper<BookDto, BookEntity> mapper;

    public BookService(DaoManager daoManager, Mapper<BookDto, BookEntity> mapper) {
        this.daoManager = daoManager;
        this.mapper = mapper;
    }

    public BookDto findBookById(Integer id) {
        if (id == null || id <= 0) {
            throw new IncorrectIdException(id);
        }
        BookEntity book = daoManager.operationFindBookById(id);
        if (book == null) {
            throw new BookNotFoundException(id);
        }
        return mapper.mappingEntityToDto(book);
    }

    public List<BookDto> getAllBook(String criteria) {
        List<BookEntity> books = daoManager.operationGetAllBook(criteria);
        if (books.isEmpty()) {
            throw new ListBookEmptyException();
        } else {
            return mapper.mappingEntityListToListDto(books);
        }
    }

    public void addBookOnWarehouse(Integer id) {
        if (id == null || id <= 0) {
            throw new IncorrectIdException(id);
        }

        BookEntity book = daoManager.operationFindBookById(id);
        if (book == null) {
            throw new BookNotFoundException(id);
        }
        daoManager.operationAddBookWarehouse(id);
    }

    public void writeBookOnWarehouse(Integer id) {
        if (id == null || id <= 0) {
            throw new IncorrectIdException(id);
        }

        BookEntity book = daoManager.operationFindBookById(id);
        if (book == null) {
            throw new BookNotFoundException(id);
        }

        daoManager.operationWriteBookWarehouse(id);
    }

    public List<BookDto> getStaleBook(String criteria) {

        List<BookEntity> staleBooks = daoManager.operationGetStaleBook(countMonthDefineStaleBook, criteria);
        if (staleBooks.isEmpty()) {
            throw new ListBookEmptyException();
        } else {
            return mapper.mappingEntityListToListDto(staleBooks);
        }
    }
}
