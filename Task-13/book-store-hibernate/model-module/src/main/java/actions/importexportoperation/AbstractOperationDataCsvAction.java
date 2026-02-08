package actions.importexportoperation;

import controller.Controller;
import enums.StatusOperationCsv;
import exceptions.InvalidInput;
import ui.IAction;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class AbstractOperationDataCsvAction implements IAction {

    protected Controller controller;
    protected String[] entitys;
    protected Scanner input = new Scanner(System.in);
    protected final String messageOperation;

    public AbstractOperationDataCsvAction(Controller controller, String[] entitys, String messageOperation) {
        this.controller = controller;
        this.entitys = entitys;
        this.messageOperation = messageOperation;
    }

    @Override
    public void execute() {
        System.out.println("Выберите сущность, которую хотите " + messageOperation + '.');
        int index = 1;
        for (String entity : entitys) {
            System.out.println(index + ". " + entity);
            index++;
        }
        System.out.print("Выбор: ");
        try {
            int choiceUser = processingNumUser();
            StatusOperationCsv resultOperationCsv = operationCsv(choiceUser);
            printResultOperationCsv(resultOperationCsv);
        } catch (InvalidInput e) {
            System.out.println("Ошибка! " + e.getMessage() + " попробуйте снова.");
            input.nextLine();
            execute();
        }

    }

    public abstract StatusOperationCsv operationCsv(int choiceUser);

    public void printResultOperationCsv(StatusOperationCsv statusOperationCsv) {
        switch (statusOperationCsv) {
            case ENTITY_EXPORT_CSV -> System.out.println("Сущность экспортирована.");
            case ENTITY_IMPORT_CSV -> System.out.println("Сущность импортирована.");
            case ERROR_ENTITY_EXPORT_CSV -> System.out.println("Ошибка при импортировании сущности.");
            case ERROR_ENTITY_IMPORT_CSV -> System.out.println("Ошибка при экспортировании сущности.");
            case INCORRECT_CHOICE -> {
                System.out.println("Некорректный выбор, попробуйте снова");
                execute();
            }
        }
    }

    public int processingNumUser() throws InvalidInput {
        try {
            int choiceUser = input.nextInt();
            input.nextLine();
            return choiceUser;
        } catch (InputMismatchException e) {
            throw new InvalidInput("Некорректное число");
        }
    }
}
