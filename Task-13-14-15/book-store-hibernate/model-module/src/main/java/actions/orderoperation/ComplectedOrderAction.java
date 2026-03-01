package actions.orderoperation;

import controller.Controller;
import enums.StatusOperationOrder;
import ui.IAction;

import java.util.Scanner;

public class ComplectedOrderAction extends AbstractOperationOrderAction {

    public ComplectedOrderAction(Controller controller, int countTry, String messageOperation, String messageSuccess, String messageError) {
        super(controller, countTry, messageOperation, messageSuccess, messageError);
    }

    @Override
    public StatusOperationOrder operationOrder(int orderId) {
        return controller.complectedOrder(orderId);
    }
}
