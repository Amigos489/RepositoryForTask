package senla.course.actions;

import senla.course.ui.IAction;
import senla.course.ui.Navigator;

public class BackToMainMenuAction implements IAction {

    private Navigator navigator;

    public BackToMainMenuAction(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void execute() {
        navigator.backToMainMenu();
    }
}
