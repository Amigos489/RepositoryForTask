package actions;

import ui.IAction;
import ui.console.ConsoleMenu;
import ui.console.ConsoleNavigator;

public class OpenMenuAction implements IAction {

    private ConsoleNavigator navigator;
    private ConsoleMenu menu;

    public OpenMenuAction(ConsoleNavigator navigator, ConsoleMenu menu) {
        this.navigator = navigator;
        this.menu = menu;
    }

    @Override
    public void execute() {
        navigator.setCurrentMenu(menu);
    }
}
