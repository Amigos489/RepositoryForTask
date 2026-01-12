import java.util.Comparator;

public class SortedBookByDateOfPublication implements Comparator<Book> {
    
    @Override
    public int compare(Book book1, Book book2) {

        return book1.getDateOfPublication().
                compareTo(book2.getDateOfPublication());

    }

}