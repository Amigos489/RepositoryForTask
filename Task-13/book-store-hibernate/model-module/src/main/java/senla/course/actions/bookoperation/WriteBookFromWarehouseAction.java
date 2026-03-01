package senla.course.actions.bookoperation;

import senla.course.controller.Controller;
import senla.course.enums.StatusOperationBook;

public class WriteBookFromWarehouseAction extends AbstractOperationBookAction {

    public WriteBookFromWarehouseAction(Controller controller, int countTry, String messageOperation, String messageSuccess, String messageError) {
        super(controller, countTry, messageOperation, messageSuccess, messageError);
    }

    @Override
    public StatusOperationBook operationBook(int bookId) {
        return controller.writeBookToWarehouse(bookId);
    };
}
