package actions.bookoperation;

import actions.AbstractGetInfoAction;
import controller.Controller;
import model.Book;

public class GetInfoBookAction extends AbstractGetInfoAction<Book> {

    public GetInfoBookAction(Controller controller, String messageGetInfo, String messageNotFound) {
        super(controller, messageGetInfo, messageNotFound);
    }

    @Override
    public Book getInfo(int bookId) {
        return controller.getInfoBook(bookId);
    }

}
