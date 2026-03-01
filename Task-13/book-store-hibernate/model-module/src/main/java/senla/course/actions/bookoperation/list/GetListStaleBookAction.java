package senla.course.actions.bookoperation.list;

import senla.course.actions.AbstractGetSortedListAction;
import senla.course.controller.Controller;
import senla.course.model.Book;

import java.util.List;

public class GetListStaleBookAction extends AbstractGetSortedListAction<Book> {

    public GetListStaleBookAction(Controller controller, String[] sortedCriterias) {
        super(controller, sortedCriterias);
    }

    @Override
    public List<Book> getSortedList(int choiceUser) {
        return controller.getSortedListStaleBook(choiceUser);
    }
}
