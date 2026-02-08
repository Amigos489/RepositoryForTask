package senla.course.actions;

import senla.course.controller.Controller;
import senla.course.model.GettingInfo;
import senla.course.ui.IAction;

import java.util.List;
import java.util.Scanner;

public abstract class AbstractGetSortedListAction<T extends GettingInfo> implements IAction {

    protected Controller controller;
    protected String[] sortedCriterias;
    protected Scanner input = new Scanner(System.in);

    public AbstractGetSortedListAction(Controller controller, String[] sortedCriterias) {
        this.controller = controller;
        this.sortedCriterias = sortedCriterias;
    }

    @Override
    public void execute() {
        int choiceUser = printCriteriaSorted();
        List<T> sortedList = getSortedList(choiceUser);
        if (sortedList.isEmpty()) {
            System.out.println("Список пустой");
            return;
        }
        printList(sortedList);
    }

    public int printCriteriaSorted() {
        System.out.println("Введите критерий сортировки. (Чтобы не сортировать, укажите другое число.)");
        int index = 1;
        for (String nameCriteria : sortedCriterias) {
            System.out.println(index + ". " + nameCriteria);
            index++;
        }
        System.out.print("Выбор: ");
        if (input.hasNextInt()) {
            int choiceUser = input.nextInt();
            input.nextLine();
            return choiceUser;
        } else {
            System.out.println("Введено некорректное значение, попробуйте снова");
            input.nextLine();
            printCriteriaSorted();
            return -1; /* Убрать */
        }
    }

    public abstract List<T> getSortedList(int choiceUser);

    public void printList(List<T> entitys) {
        for (T entity : entitys) {
            System.out.println(entity.getInfo());
        }
    }
}
