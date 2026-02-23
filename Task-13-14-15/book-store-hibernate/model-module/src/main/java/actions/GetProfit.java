package actions;

import controller.Controller;
import exceptions.InvalidInput;
import ui.IAction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class GetProfit implements IAction {

    private Controller controller;
    private Scanner input = new Scanner(System.in);

    public GetProfit(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            System.out.print("Введите начальную дату: ");
            LocalDate startDate = processingDataUser();
            System.out.print("Введите конечную дату: ");
            LocalDate endDate = processingDataUser();
            BigDecimal profit = controller.getProfit(startDate, endDate);
            System.out.println("Прибыль с " + startDate + " по " + endDate + " составляет: " + profit);
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
