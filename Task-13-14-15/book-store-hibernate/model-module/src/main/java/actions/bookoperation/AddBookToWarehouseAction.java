package actions.bookoperation;

import controller.Controller;
import enums.StatusOperationBook;

public class AddBookToWarehouseAction extends AbstractOperationBookAction {

    public AddBookToWarehouseAction(Controller controller, int countTry, String messageOperation, String messageSuccess, String messageError) {
        super(controller, countTry, messageOperation, messageSuccess, messageError);
    }

    @Override
    public StatusOperationBook operationBook(int bookId) {
        return controller.addBookToWarehouse(bookId);
    };
}
