package actions.bookoperation.list;

import actions.AbstractGetSortedListAction;
import controller.Controller;
import model.Book;

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
