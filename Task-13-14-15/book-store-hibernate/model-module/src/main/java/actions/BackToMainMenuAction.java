package actions;

import ui.IAction;
import ui.Navigator;

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
