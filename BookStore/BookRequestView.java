import java.util.ArrayList;

public class BookRequestView {

/* Отобразить список запросов */
    public static void printBookRequests(ArrayList<BookRequest> bookRequests) {

        for (BookRequest bookRequest : bookRequests) {
            System.out.println("\n");
            printBookRequestInfo(bookRequest);
            System.out.println("\n");
        }

    }

    /* Отобразить информацию о запросе */
    public static void printBookRequestInfo(BookRequest bookRequest) {

        System.out.println("ID запроса: " + bookRequest.getId());
        System.out.println("Книга: " + bookRequest.getBookName());
        System.out.println("Количество: " + bookRequest.getRequestCount());
        System.out.println("Дата: " + bookRequest.getRequestDate());
        
    }

}