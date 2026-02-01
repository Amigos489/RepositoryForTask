package actions.orderoperation;

import actions.AbstractGetInfoAction;
import controller.Controller;
import model.Order;

public class GetInfoOrderAction extends AbstractGetInfoAction<Order> {

    public GetInfoOrderAction(Controller controller, String messageGetInfo, String messageNotFound) {
        super(controller, messageGetInfo, messageNotFound);
    }

    @Override
    public Order getInfo(int orderId) {
        return controller.getInfoOrder(orderId);
    }

}
