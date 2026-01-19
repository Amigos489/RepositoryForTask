package sorted;

import model.BookRequest;
import java.util.Comparator;

public class SortedBookRequestByBookID implements Comparator<BookRequest> {

    @Override
    public int compare(BookRequest bookRequest1, BookRequest bookRequest2) {

        return Integer.compare(bookRequest1.getBookId(), bookRequest2.getBookId());
    }
}