package actions.orderoperation.list;

import actions.AbstractGetSortedListAction;
import controller.Controller;
import exceptions.InvalidInput;
import model.Order;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class GetListComplectedOrderAction extends AbstractGetSortedListAction<Order> {

    public GetListComplectedOrderAction(Controller controller, String[] sortedCriterias) {
        super(controller, sortedCriterias);
    }

    @Override
    public List<Order> getSortedList(int choiceUser) {
        try {
            System.out.print("Введите начальную дату: ");
            LocalDate startDate = processingDataUser();
            System.out.print("Введите конечную дату: ");
            LocalDate endDate = processingDataUser();
            return controller.getSortedListComplectedOrder(choiceUser, startDate, endDate);
        } catch (InvalidInput e) {
            System.out.println("Ошибка! " + e.getMessage() + " Попробуйте снова.");
            getSortedList(choiceUser);
            return null;
        }
    }

    public LocalDate processingDataUser() throws InvalidInput {
        try {
            String userString = input.nextLine();
            LocalDate dateUser = LocalDate.parse(userString);
            return dateUser;
        } catch (DateTimeParseException e) {
            throw new InvalidInput("Некорректная дата.");
        }
    }
}
