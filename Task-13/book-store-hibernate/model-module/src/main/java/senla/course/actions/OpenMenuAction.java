package senla.course.actions;

import senla.course.ui.IAction;
import senla.course.ui.Navigator;
import senla.course.ui.console.ConsoleMenu;


public class OpenMenuAction implements IAction {

    private Navigator navigator;
    private ConsoleMenu menu;

    public OpenMenuAction(Navigator navigator, ConsoleMenu menu) {
        this.navigator = navigator;
        this.menu = menu;
    }

    @Override
    public void execute() {
        navigator.setCurrentMenu(menu);
    }
}
