package senla.course.sorted.book;

import senla.course.model.Book;
import java.util.Comparator;

public class SortedBookByAvailbility implements Comparator<Book> {
    @Override
    public int compare(Book book1, Book book2) {
        return Boolean.compare(book1.getAvailability(), book2.getAvailability());
    }
}
