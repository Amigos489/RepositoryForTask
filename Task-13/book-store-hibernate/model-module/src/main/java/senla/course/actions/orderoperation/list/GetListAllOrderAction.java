package senla.course.actions.orderoperation.list;

import senla.course.actions.AbstractGetSortedListAction;
import senla.course.controller.Controller;
import senla.course.model.Order;

import java.util.List;

public class GetListAllOrderAction extends AbstractGetSortedListAction<Order> {

    public GetListAllOrderAction(Controller controller, String[] sortedCriterias) {
        super(controller, sortedCriterias);
    }

    @Override
    public List<Order> getSortedList(int choiceUser) {
        return controller.getSortedListAllOrder(choiceUser);
    }
}
