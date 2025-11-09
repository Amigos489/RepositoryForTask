import java.util.Comparator;

public class SortedBookByAvailability implements Comparator<Book>{

    @Override
    public int compare(Book book1, Book book2) {

        if (book1.getAvailability() == book2.getAvailability()) {
            return 0;
        } else if (book1.getAvailability() && !book2.getAvailability()) {
            return -1;
        } else {
            return 1;
        }

    }

}