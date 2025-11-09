import java.util.Comparator;

public class SortedRequestByRequestCount implements Comparator<BookRequest> {

    @Override
    public int compare(BookRequest bookRequest1, BookRequest bookRequest2) {

        return Integer.compare(bookRequest1.getRequestCount(), bookRequest2.getRequestCount());

    }

}