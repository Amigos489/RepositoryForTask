package ui.console;

import exceptions.InvalidInput;
import ui.Navigator;

import java.util.Scanner;

public class ConsoleNavigator implements Navigator {

    private ConsoleMenu currentMenu;
    private ConsoleMenu mainMenu;
    private Scanner input = new Scanner(System.in);

    public ConsoleNavigator() {}

    public ConsoleNavigator(ConsoleMenu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public ConsoleNavigator(ConsoleMenu currentMenu, ConsoleMenu mainMenu) {

        this.currentMenu = currentMenu;
        this.mainMenu = mainMenu;
    }

    @Override
    public void backToMainMenu() {
        this.currentMenu = mainMenu;
    }

    @Override
    public int printMenu() {
        System.out.println(currentMenu.getNameMenu());
        System.out.println("Выберите действие:");
        int numItem = 1;
        for (ConsoleMenuItem item : currentMenu.getMenuItems()) {
            System.out.print(numItem + ". ");
            numItem++;
            System.out.println(item.getTitle());
        }
        System.out.print("Выбор: ");
        try {
            int choice = processingUserInput();
            navigate(choice);
            return choice;
        } catch (InvalidInput e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    @Override
    public int getCountItemInMenu() {
        return currentMenu.getMenuItems().size();
    }

    @Override
    public void navigate(int index) {
        int countItems = currentMenu.getMenuItems().size();
        for (int i = 0; i < countItems; i++) {
            if (index == i + 1) {
                System.out.println("Выбран пункт: " + index);
                System.out.println("Действие: " + currentMenu.getMenuItems().get(i).getTitle());
                currentMenu.getMenuItems().get(i).doAction();
                return;
            }
        }
        System.out.println("Выбран некорректный пункт. Попробуйте снова.");
        printMenu();
    }

    public int processingUserInput() throws InvalidInput {
        if (input.hasNextInt()) {
            int choice = input.nextInt();
            input.nextLine();
            return choice;
        } else {
            input.nextLine();
            throw new InvalidInput("Ошибка! Некорректное число.");
        }
    }

    public void setCurrentMenu(ConsoleMenu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public void setMainMenu(ConsoleMenu mainMenu) {
        this.mainMenu = mainMenu;
    }
}
