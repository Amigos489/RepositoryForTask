package senla.course.ui.console;

import senla.course.ui.IAction;
import senla.course.ui.MenuController;

public class ExitAppAction implements IAction {

    private MenuController menuController;

    public ExitAppAction(MenuController menuController) {
        this.menuController = menuController;
    }

    @Override
    public void execute() {

        menuController.exitFromProgramm();
    }
}
