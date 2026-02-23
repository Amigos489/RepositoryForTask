package mapping;

import entitys.BookEntity;
import model.Book;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BookMapping extends AbstractMapper<Book, BookEntity> {

    @Override
    public Book entityToModelMapping(BookEntity bookEntity) {
        if (bookEntity == null) {
            return null;
        }
        int bookId = bookEntity.getBookId();
        String nameBook = bookEntity.getNameBook();
        String authorBook = bookEntity.getAuthorBook();
        LocalDate datePublication = bookEntity.getDatePublication();
        LocalDate dateAddWarehouse = bookEntity.getDateAddWarehouse();
        BigDecimal price = bookEntity.getPrice();
        boolean availability = bookEntity.isAvailability();
        return new Book(bookId, nameBook, authorBook, datePublication, dateAddWarehouse, price, availability);
    }

    /* Новые книги не создаём */
    @Override
    public BookEntity modelToEntityMapping(Book model) {
        return null;
    }
}
