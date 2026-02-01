package actions.bookoperation;

import controller.Controller;
import enums.StatusOperationBook;
import enums.StatusOrder;
import ui.IAction;

import java.util.Scanner;

public abstract class AbstractOperationBookAction implements IAction {

    protected Controller controller;
    protected int currentCountTry;
    protected int maxCountTry;
    protected Scanner input = new Scanner(System.in);
    protected final String messageOperation;
    protected final String messageError;
    protected final String messageSuccess;

    public AbstractOperationBookAction(Controller controller, int countTry, String messageOperation, String messageSuccess, String messageError) {
        this.controller = controller;
        this.currentCountTry = countTry;
        this.maxCountTry = countTry;
        this.messageOperation = messageOperation;
        this.messageSuccess = messageSuccess;
        this.messageError = messageError;
    }

    @Override
    public void execute() {
        System.out.print("Введите id книги, которую хотите " + messageOperation + ": ");
        if (input.hasNextInt()) {
            int bookId = input.nextInt();
            input.nextLine();
            StatusOperationBook resultOperation = operationBook(bookId);
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

    public abstract StatusOperationBook operationBook(int bookId);

    public void printResultOperationBook(StatusOperationBook statusOperationBook) {
        switch (statusOperationBook) {
            case BOOK_ADD_WAREHOUSE -> { System.out.println("Книга добавлена на склад."); }
            case BOOK_ALREADY_ADD_WAREHOUSE -> { System.out.println("Книга уже добавлена на склад."); }
            case BOOK_WRITE_WAREHOUSE -> { System.out.println("Книга списана со склада."); }
            case BOOK_ALREADY_WRITE_WAREHOUSE -> { System.out.println("Книга уже списана со склада."); }
            case BOOK_NOT_FOUND -> { System.out.println("Книга не найдена."); }
        }
    }
}
