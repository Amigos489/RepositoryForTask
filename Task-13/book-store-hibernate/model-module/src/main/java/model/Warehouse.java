package model;

import annotations.ConfigProperty;
import dao.BookDao;
import enums.StatusOperationBook;
import exception.EntityNotFound;
import mapping.BookMapping;
import sorted.book.*;

import java.util.Collections;
import java.util.List;

public class Warehouse {

    private List<Book> books;
    @ConfigProperty(type = Integer.class)
    private int countMonthDefineStaleBook;
    private BookDao bookDao;
    private BookMapping mapper;

    public Warehouse() {}

    public Warehouse(BookDao bookDao, BookMapping mapper) {
        this.bookDao = bookDao;
        this.mapper = mapper;
    }

    public void initDataFromDataBase() {
        this.books = mapper.entityListToModelListMapping(bookDao.findAll());
    }

    /* Поиск книги по id */
    public Book findBookById(int id) {
        Book book = mapper.entityToModelMapping(bookDao.findBookById(id));
        return book;
    }

    /* Проверка наличия книги */
    public boolean isBookAvailable(int id) {
        List<Book> books = mapper.entityListToModelListMapping(bookDao.findAll());
        for(Book book : books) {
            if (book.getId() == id && book.getAvailability()) {
                return true;
            }
        }
        return false;
    }

    /* Добавить книгу по id */
    public StatusOperationBook addBookById(int id) {
        try {
            bookDao.operationBookById(id, true);
            return StatusOperationBook.BOOK_ADD_WAREHOUSE;
        } catch (EntityNotFound e) {
            return StatusOperationBook.BOOK_NOT_FOUND;
        }
    }

    /* Списать книгу со склада по id */
    public StatusOperationBook writeBookById(int id) {
        try {
            bookDao.operationBookById(id, false);
            return StatusOperationBook.BOOK_WRITE_WAREHOUSE;
        } catch (EntityNotFound e) {
            return StatusOperationBook.BOOK_NOT_FOUND;
        }
    }

    /* Получить список "залежавшихся" книг */
    public List<Book> getStaleBooks() {
        return mapper.entityListToModelListMapping(bookDao.getStaleBook(countMonthDefineStaleBook));
    }

    public List<Book> sortedListAllBook(int choiceUser) {
        List<Book> books = mapper.entityListToModelListMapping(bookDao.findAll());
        switch (choiceUser) {
            case 1:
                Collections.sort(books, new SortedBookByName());
                return books;
            case 2:
                Collections.sort(books, new SortedBookByDatePublication());
                return books;
            case 3:
                Collections.sort(books, new SortedBookByPrice());
                return books;
            case 4:
                Collections.sort(books, new SortedBookByAvailbility());
                return books;
            default:
                return books;
        }
    }

    public List<Book> sortedListStaleBook(int choiceUser) {
        List<Book> staleBook = getStaleBooks();
        switch (choiceUser) {
            case 1:
                Collections.sort(staleBook, new SortedBookByDateAddWarehouse());
            case 2:
                Collections.sort(staleBook, new SortedBookByPrice());
            default:
                return staleBook;
        }
    }

    public List<Book> getAllBooks() {
        List<Book> books = mapper.entityListToModelListMapping(bookDao.findAll());
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
