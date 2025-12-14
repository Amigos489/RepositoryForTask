import java.util.Comparator;

public class SortedBookRequestByNameBook implements Comparator<BookRequest> {

    @Override
    public int compare(BookRequest bookRequest1, BookRequest bookRequest2) {

        return bookRequest1.getBookName().compareTo(bookRequest2.getBookName());

    }

}