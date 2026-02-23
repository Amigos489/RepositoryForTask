package actions.requestoperation;

import actions.AbstractGetSortedListAction;
import controller.Controller;
import model.Request;

import java.util.List;

public class GetListAllRequestAction extends AbstractGetSortedListAction<Request> {

    public GetListAllRequestAction(Controller controller, String[] sortedCriterias) {
        super(controller, sortedCriterias);
    }

    @Override
    public List<Request> getSortedList(int choiceUser) {
        return controller.getSortedListAllRequest(choiceUser);
    }
}
