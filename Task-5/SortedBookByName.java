import java.util.Comparator;

public class SortedBookByName implements Comparator<Book>{

    @Override
    public int compare(Book book1, Book book2) {

        return book1.getNameBook().compareTo(book2.getNameBook());

    }

}