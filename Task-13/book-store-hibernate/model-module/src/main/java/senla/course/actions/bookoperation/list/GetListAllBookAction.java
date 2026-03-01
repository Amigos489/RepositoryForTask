package senla.course.actions.bookoperation.list;

import senla.course.actions.AbstractGetSortedListAction;
import senla.course.controller.Controller;
import senla.course.model.Book;

import java.util.List;

public class GetListAllBookAction extends AbstractGetSortedListAction<Book> {

    public GetListAllBookAction(Controller controller, String[] sortedCriterias) {
        super(controller, sortedCriterias);
    }

    @Override
    public List<Book> getSortedList(int choiceUser) {
        return controller.getSortedListAllBook(choiceUser);
    }
}
