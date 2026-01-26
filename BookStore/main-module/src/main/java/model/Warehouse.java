package model;

import dao.OperationTransaction;
import di.Inject;
import modeldao.BooksDAO;
import status.StatusAddBook;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Warehouse {

    private int currentCapacity;
    private final int maximumCapacity;
    private final int cntMountStale;
    private ArrayList<Book> listBook;
    @Inject
    private BooksDAO booksDAO;

    /* Конструктор по умолчанию */
    public Warehouse() {
        this.currentCapacity = 0;
        this.maximumCapacity = 100;
        this.cntMountStale = 6;
    }

    /* Конструктор со всеми параметрами */
    public Warehouse(int currentCapacity, int maximumCapacity, int cntMountStale) {
        this.currentCapacity = currentCapacity;
        this.maximumCapacity = maximumCapacity;
        this.cntMountStale = cntMountStale;
    }

    public void initFromDAO() {
        this.listBook = new ArrayList<>(booksDAO.getAll());
    }

    /* Добавление книги на склад */
    public StatusAddBook addToWarehouse(Book book) {
        int availableSpace = this.maximumCapacity - this.currentCapacity;
        if (availableSpace <= 0) {
            return StatusAddBook.FAIL;
        }
        booksDAO.accomplishmentTransaction(OperationTransaction.START);
        try {
            if (book.getNumberOfCopies() > availableSpace) {
                book.setNumberOfCopies(availableSpace);
                this.currentCapacity += availableSpace;
                book.setAvailability(true);
                booksDAO.save(book);                                //Добавили книги в бд
                booksDAO.accomplishmentTransaction(OperationTransaction.COMMIT);
                listBook.add(book);
                return StatusAddBook.ONLYPART;
            }
            this.currentCapacity += book.getNumberOfCopies();
            book.setAvailability(true);
            booksDAO.save(book);                                    //Добавили книги в бд
            booksDAO.accomplishmentTransaction(OperationTransaction.COMMIT);
            listBook.add(book);
            return StatusAddBook.SUCCESSFULY;
        } catch (SQLException e) {
            e.printStackTrace();
            booksDAO.accomplishmentTransaction(OperationTransaction.ROLLBACK);
            return StatusAddBook.FAIL;
        } finally {
            booksDAO.accomplishmentTransaction(OperationTransaction.END);
        }
    }

    /* Списание книги со склада */
    public boolean writeFromWarehouse(String nameBook) {
        try {
            booksDAO.accomplishmentTransaction(OperationTransaction.START);
            ArrayList<Book> books = (ArrayList<Book>) booksDAO.getAll();
            for (Book book : books) {
                if (book.getNameBook().equalsIgnoreCase(nameBook.trim())) {
                    int numberOfCopies = book.getNumberOfCopies();
                    book.setNumberOfCopies(0);
                    book.setAvailability(false);
                    this.currentCapacity -= numberOfCopies;
                    booksDAO.update(book);
                    booksDAO.accomplishmentTransaction(OperationTransaction.COMMIT);
                    booksDAO.accomplishmentTransaction(OperationTransaction.END);
                    return true; // книга успешно списана
                }
            }
            booksDAO.accomplishmentTransaction(OperationTransaction.END);
            return false; // книга не найдена
        } catch (SQLException e) {
            booksDAO.accomplishmentTransaction(OperationTransaction.ROLLBACK);
            booksDAO.accomplishmentTransaction(OperationTransaction.END);
            return false; // книга не найдена
        }
    }

    /* Получить список залежавшихся книг */
    public ArrayList<Book> getStaleBooks() {
        ArrayList<Book> stale = new ArrayList<>();

        ArrayList<Book> books = (ArrayList<Book>) booksDAO.getAll();
        for (Book book : books) {
            // Проверяем, что книга есть на складе и не продана больше 6 месяцев
            if (book.getDateAddedToWarehouse().isBefore(LocalDate.now().minusMonths(cntMountStale)) && book.getNumberOfCopies() > 0) {
                stale.add(book);
            }
        }
        return stale;
    }

    /* Поиск книги по названию */
    public Book findBookByName(String nameBook) {
        ArrayList<Book> books = (ArrayList<Book>) booksDAO.getAll();
        for (Book book : books) {
            if (book.getNameBook().equalsIgnoreCase(nameBook.trim())) {
                return book;
            }
        }
        return null;
    }

    /* Поиск книги по id в базе данных */
    public Book findBookByID(int id) {
        return booksDAO.getByID(id);
    }

    /* Геттеры и сеттеры */
    public int getCurrentCapacity() {
        return this.currentCapacity;
    }

    public int getMaximumCapacity() {
        return this.maximumCapacity;
    }

    public ArrayList<Book> getListBooks() {
        return (ArrayList<Book>) booksDAO.getAll();
    }

    public void setListBooks(ArrayList<Book> books) {
        this.listBook = books;
    }

    public boolean setCurrentCapacity(int newValue) {
        if (newValue <= this.maximumCapacity && newValue >= 0) {
            this.currentCapacity = newValue;
            return true;
        } else {
            return false;
        }
    }
}

