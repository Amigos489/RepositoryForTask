package actions.orderoperation;

import controller.Controller;
import enums.StatusOrder;
import exceptions.InvalidInput;
import ui.IAction;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CreateOrderAction implements IAction {

    private Controller controller;
    private Scanner input = new Scanner(System.in);

    public CreateOrderAction(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            System.out.print("Введите id книги, на которую хотите сделать заказ: ");
            int bookId = processingNumUser();
            input.nextLine();
            System.out.print("Укажите свою почту: ");
            String emailUser = processingStringUser();
            StatusOrder resultCreateOrder = controller.createOrder(bookId, emailUser);
            printResultCreateOrder(resultCreateOrder);
        } catch (InvalidInput e) {
            System.out.println("Ошибка! " + e.getMessage() + ", попробуйте снова.");
            execute();
        }
    }

    private void printResultCreateOrder(StatusOrder statusOrder) {
        switch (statusOrder) {
            case NEW -> { System.out.println( "Заказ успешно создан." ); }
            case WAITING -> { System.out.println( "Книги нет в наличии, создан запрос на книгу, ожидайте." ); }
            case ERROR_CREATE_ORDER -> { System.out.println( "Возникла ошибка при указании данных, заказ не был создан." ); }
        }
    }

    public String processingStringUser() throws InvalidInput {
        String userString = input.nextLine();
        if (!userString.isEmpty()) {
            return userString;
        } else {
            throw new InvalidInput("Пустая строка");
        }
    }

    public int processingNumUser() throws InvalidInput {
        try {
            int userNum = input.nextInt();
            return userNum;
        } catch (InputMismatchException e) {
            input.nextLine();
            throw new InvalidInput("Некорректное число");
        }
    }
}
