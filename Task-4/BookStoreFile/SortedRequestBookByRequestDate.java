import java.util.Comparator;

public class SortedRequestBookByRequestDate implements Comparator<BookRequest> {

    @Override
    public int compare(BookRequest bookRequest1, BookRequest bookRequest2) {
        return bookRequest1.getRequestDate().compareTo(bookRequest2.getRequestDate());
    }

}
