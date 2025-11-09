import java.util.Comparator;

public class SortedBookByDateAddedToWarehouse implements Comparator<Book> {

    @Override
    public int compare(Book book1, Book book2) {

        return book1.getDateAddedToWarehouse().
                compareTo(book2.getDateAddedToWarehouse());

    }

}