package ui.console;

import ui.AbstractUserInterfaceFactory;

public class ConsoleUserInterfaceFactory implements AbstractUserInterfaceFactory {

    @Override
    public ConsoleMenu makeMenu() {
        return null;
    }

    @Override
    public ConsoleMenuItem makeMenuItem() {
        return null;
    }

    @Override
    public ConsoleNavigator makeNavigator() {
        return null;
    }

    @Override
    public ConsoleMenuController makeMenuController() {
        return null;
    }
}
