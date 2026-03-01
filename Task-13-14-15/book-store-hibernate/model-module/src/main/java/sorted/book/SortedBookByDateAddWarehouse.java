package sorted.book;

import model.Book;

import java.util.Comparator;

public class SortedBookByDateAddWarehouse implements Comparator<Book> {
    @Override
    public int compare(Book book1, Book book2) {
        return book1.getDateAddWarehouse().compareTo(book2.getDateAddWarehouse());
    }
}
