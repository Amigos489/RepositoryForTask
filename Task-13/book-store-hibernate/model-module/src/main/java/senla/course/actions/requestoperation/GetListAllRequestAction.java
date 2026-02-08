package senla.course.actions.requestoperation;

import senla.course.actions.AbstractGetSortedListAction;
import senla.course.controller.Controller;
import senla.course.model.Request;

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
