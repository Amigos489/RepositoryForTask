package senla.course.actions.orderoperation;

import senla.course.actions.AbstractGetInfoAction;
import senla.course.controller.Controller;
import senla.course.model.Order;

public class GetInfoOrderAction extends AbstractGetInfoAction<Order> {

    public GetInfoOrderAction(Controller controller, String messageGetInfo, String messageNotFound) {
        super(controller, messageGetInfo, messageNotFound);
    }

    @Override
    public Order getInfo(int orderId) {
        return controller.getInfoOrder(orderId);
    }

}
