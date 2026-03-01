package ui.console;

import ui.IAction;
import ui.MenuController;

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
