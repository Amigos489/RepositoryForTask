package model;

import annotations.ConfigProperty;
import dao.BookDaoImpl;
import dao.DaoManager;
import enums.StatusOperationBook;
import exception.EntityListEmpty;
import exception.EntityNotFound;
import mapping.BookMapping;
import sorted.book.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Warehouse {

    private List<Book> books;
    @ConfigProperty(type = Integer.class)
    private int countMonthDefineStaleBook;
    private BookMapping mapper;
    private DaoManager daoManager;

    public Warehouse() {}

    public Warehouse(BookMapping mapper, DaoManager daoManager) {
        this.mapper = mapper;
        this.daoManager = daoManager;
    }

    public void initDataFromDataBase() {

        try {
            this.books = mapper.entityListToModelListMapping(daoManager.operationGetAllBook());
        } catch (EntityListEmpty e) {
            this.books = new ArrayList<Book>();
        }
    }

    /* Поиск книги по id */
    public Book findBookById(int id) {
        try {
            Book book = mapper.entityToModelMapping(daoManager.operationFindBookById(id));
            return book;
        } catch (EntityNotFound e) {
            return null;
        }
    }

    /* Проверка наличия книги */
    public boolean isBookAvailable(int id) {
        try {
            List<Book> books = mapper.entityListToModelListMapping(daoManager.operationGetAllBook());for(Book book : books) {
                if (book.getId() == id && book.getAvailability()) {
                    return true;
                }
            }
            return false;
        } catch (EntityListEmpty e) {
            return false;
        }
    }

    /* Добавить книгу на склад по id */
    public StatusOperationBook addBookById(int bookId) {
        try {
            daoManager.operationAddBookWarehouse(bookId);
            return StatusOperationBook.BOOK_ADD_WAREHOUSE;
        } catch (EntityNotFound e) {
            return StatusOperationBook.BOOK_NOT_FOUND;
        }
    }

    /* Списать книгу со склада по id */
    public StatusOperationBook writeBookById(int bookId) {
        try {
            daoManager.operationWriteBookWarehouse(bookId);
            return StatusOperationBook.BOOK_WRITE_WAREHOUSE;
        } catch (EntityNotFound e) {
            return StatusOperationBook.BOOK_NOT_FOUND;
        }
    }

    /* Получить список "залежавшихся" книг */
    public List<Book> getStaleBooks() {
        List<Book> staleBook = new ArrayList<Book>();
        try {
            staleBook =  mapper.entityListToModelListMapping(daoManager.operationGetStaleBook(countMonthDefineStaleBook));
            return staleBook;
        } catch (EntityListEmpty e) {
            return staleBook;
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

    public List<Book> sortedListAllBook(int choiceUser) {
        List<Book> books = new ArrayList<Book>();
        try {
            books = mapper.entityListToModelListMapping(daoManager.operationGetAllBook());

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
        } catch (EntityListEmpty e) {
            return books;
        }
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<Book>();
        try {
            books = mapper.entityListToModelListMapping(daoManager.operationGetAllBook());
            return books;
        } catch (EntityListEmpty e) {
            return books;
        }
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
