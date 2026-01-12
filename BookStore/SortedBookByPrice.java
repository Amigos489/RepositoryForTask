import java.util.Comparator;

public class SortedBookByPrice implements Comparator<Book> {

    @Override
    public int compare(Book book1, Book book2) {
        return book1.getPrice().compareTo(book2.getPrice());
    }
}
