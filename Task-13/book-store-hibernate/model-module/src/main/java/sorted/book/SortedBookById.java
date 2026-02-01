package sorted.book;

import model.Book;
import java.util.Comparator;

public class SortedBookById implements Comparator<Book> {
    @Override
    public int compare(Book book1, Book book2) {

        return Integer.compare(book1.getId(), book2.getId());
    }
}
