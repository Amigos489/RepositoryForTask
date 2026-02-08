package actions.orderoperation;

import controller.Controller;
import enums.StatusOperationOrder;
import enums.StatusOrder;
import ui.IAction;

import java.util.Scanner;

public class CloseOrderAction extends AbstractOperationOrderAction {

    public CloseOrderAction(Controller controller, int countTry, String messageOperation, String messageSuccess, String messageError) {
        super(controller, countTry, messageOperation, messageSuccess, messageError);
    }

    @Override
    public StatusOperationOrder operationOrder(int orderId) {
        return controller.closeOrder(orderId);
    }
}
