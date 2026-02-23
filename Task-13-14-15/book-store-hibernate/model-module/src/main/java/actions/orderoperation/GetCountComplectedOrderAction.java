package actions.orderoperation;

import controller.Controller;
import exceptions.InvalidInput;
import ui.IAction;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class GetCountComplectedOrderAction implements IAction {

    private Controller controller;
    private Scanner input = new Scanner(System.in);

    public GetCountComplectedOrderAction(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            System.out.print("Введите начальную дату: ");
            LocalDate startDate = processingDataUser();
            System.out.print("Введите конечную дату: ");
            LocalDate endDate = processingDataUser();
            int countComplectedOrder =  controller.getCountComplectedOrder(startDate, endDate);
            if (countComplectedOrder == 0) {
                System.out.println("Нет выполненных заказов в указанный период времени.");
                return;
            }
            System.out.println("Количество выполненных заказов с " + startDate + " по " + endDate + " составляет: " + countComplectedOrder);
        } catch (InvalidInput e) {
            System.out.println("Ошибка! " + e.getMessage() + " Попробуйте снова.");
            execute();
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
