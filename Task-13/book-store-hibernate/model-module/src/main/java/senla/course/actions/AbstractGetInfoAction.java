package senla.course.actions;

import senla.course.controller.Controller;
import senla.course.exceptions.InvalidInput;
import senla.course.model.GettingInfo;
import senla.course.ui.IAction;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class AbstractGetInfoAction<T extends GettingInfo> implements IAction {

    protected Controller controller;
    protected Scanner input = new Scanner(System.in);
    protected final String messageGetInfo;
    protected final String messageNotFound;

    public AbstractGetInfoAction(Controller controller, String messageGetInfo, String messageNotFound) {
        this.controller = controller;
        this.messageGetInfo = messageGetInfo;
        this.messageNotFound = messageNotFound;
    }

    @Override
    public void execute() {
        try {
            System.out.print(messageGetInfo);
            int entityId = processeingNumUser();
            T entity = getInfo(entityId);
            if (entity == null) {
                System.out.println(messageNotFound);
                return;
            }
            System.out.println(entity.getInfo());
        } catch (InvalidInput e) {
            System.out.println("Ошибка! " + e.getMessage() + ", попробуйте снова.");
            execute();
        }
    }

    public abstract T getInfo(int entityId);

    private int processeingNumUser() throws InvalidInput {
        try {
            int numUser = input.nextInt();
            input.nextLine();
            return numUser;
        } catch (InputMismatchException e) {
            input.nextLine();
            throw new InvalidInput("некорректное число");
        }
    }
}
