package senla.course.sorted.book;

import senla.course.model.Book;
import java.util.Comparator;

public class SortedBookByDatePublication implements Comparator<Book> {
    @Override
    public int compare(Book book1, Book book2) {
        return book1.getDatePublication().compareTo(book2.getDatePublication());
    }
}
