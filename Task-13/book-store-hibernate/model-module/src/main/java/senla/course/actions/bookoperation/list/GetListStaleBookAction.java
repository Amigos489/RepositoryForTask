package actions.bookoperation.list;

import actions.AbstractGetSortedListAction;
import controller.Controller;
import model.Book;

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
