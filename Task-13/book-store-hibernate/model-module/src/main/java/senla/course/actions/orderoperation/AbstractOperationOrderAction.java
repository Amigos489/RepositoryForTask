package senla.course.actions.orderoperation;

import senla.course.controller.Controller;
import senla.course.enums.StatusOperationOrder;
import senla.course.ui.IAction;

import java.util.Scanner;

public abstract class AbstractOperationOrderAction implements IAction {

    protected Controller controller;
    protected int currentCountTry;
    protected int maxCountTry;
    protected Scanner input = new Scanner(System.in);
    protected final String messageOperation;
    protected final String messageError;
    protected final String messageSuccess;

    public AbstractOperationOrderAction(Controller controller, int countTry, String messageOperation, String messageSuccess, String messageError) {
        this.controller = controller;
        this.currentCountTry = countTry;
        this.maxCountTry = countTry;
        this.messageOperation = messageOperation;
        this.messageSuccess = messageSuccess;
        this.messageError = messageError;
    }

    @Override
    public void execute() {
        System.out.print("Введите id заказа, который хотите " + messageOperation + ": ");
        if (input.hasNextInt()) {
            int orderId = input.nextInt();
            input.nextLine();
            StatusOperationOrder resultOperation = operationOrder(orderId);
            printResultOperationBook(resultOperation);
        } else {
            --currentCountTry;
            System.out.println("Введено некорректное число, попробуйте снова, осталось попыток: " + currentCountTry);
            input.nextLine();
            if (currentCountTry <= 0) {
                System.out.println("Слишком много неудачных попыток.");
                currentCountTry=maxCountTry;
                return;
            }
            execute();
        }
    }

    public abstract StatusOperationOrder operationOrder(int orderId);

    public void printResultOperationBook(StatusOperationOrder statusOperationBook) {
        switch (statusOperationBook) {
            case CREATE_ORDER -> { System.out.println("Заказ создан."); }
            case ERROR_CREATE_ORDER -> { System.out.println("Ошибка при создании заказа."); }
            case CLOSED_ORDER -> { System.out.println("Заказ отменён."); }
            case CLOSED_ALREADY_ORDER -> { System.out.println("Заказ уже отменён."); }
            case ORDER_COMPLECTED_BEFORE_CLOSE -> {System.out.println("Нельзя отменить заказ, так как он уже выполнен");}
            case COMPLECTED_ORDER -> { System.out.println("Заказ выполнен."); }
            case COMPLECTED_ALREADY_ORDER -> { System.out.println("Заказ уже выполнен."); }
            case ORDER_CLOSED_BEFORE_COMPLECTED -> { System.out.println("Нельзя выполнить заказ, так как он уже был отменён."); }
            case ERROR_COMPLECTED_ORDER -> { System.out.println("Невозможно выполнить заказ, пока есть активные запросы на книгу."); }
            case ORDER_NOT_FOUND -> { System.out.println("Не удалось найти заказ."); }
        }
    }
}
