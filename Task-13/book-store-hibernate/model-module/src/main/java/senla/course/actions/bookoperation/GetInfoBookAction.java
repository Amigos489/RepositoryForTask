package senla.course.actions.bookoperation;

import senla.course.actions.AbstractGetInfoAction;
import senla.course.controller.Controller;
import senla.course.model.Book;

public class GetInfoBookAction extends AbstractGetInfoAction<Book> {

    public GetInfoBookAction(Controller controller, String messageGetInfo, String messageNotFound) {
        super(controller, messageGetInfo, messageNotFound);
    }

    @Override
    public Book getInfo(int bookId) {
        return controller.getInfoBook(bookId);
    }

}
