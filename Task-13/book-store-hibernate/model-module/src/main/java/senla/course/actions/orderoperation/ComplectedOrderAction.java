package senla.course.actions.orderoperation;

import senla.course.controller.Controller;
import senla.course.enums.StatusOperationOrder;

public class ComplectedOrderAction extends AbstractOperationOrderAction {

    public ComplectedOrderAction(Controller controller, int countTry, String messageOperation, String messageSuccess, String messageError) {
        super(controller, countTry, messageOperation, messageSuccess, messageError);
    }

    @Override
    public StatusOperationOrder operationOrder(int orderId) {
        return controller.complectedOrder(orderId);
    }
}
