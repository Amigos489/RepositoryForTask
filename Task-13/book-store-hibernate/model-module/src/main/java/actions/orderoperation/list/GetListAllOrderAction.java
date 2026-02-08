package actions.orderoperation.list;

import actions.AbstractGetSortedListAction;
import controller.Controller;
import model.Order;

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
