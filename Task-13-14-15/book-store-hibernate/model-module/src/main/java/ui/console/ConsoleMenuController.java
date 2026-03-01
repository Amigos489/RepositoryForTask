package ui.console;

import ui.Builder;
import ui.MenuController;
import ui.Navigator;

public class ConsoleMenuController implements MenuController {

    private Builder builder;
    private Navigator navigator;
    private boolean isExit;

    public ConsoleMenuController(Builder builder, Navigator navigator) {
        this.builder = builder;
        this.navigator = navigator;
        this.isExit = false;
    }

    public ConsoleMenuController(Navigator navigator) {
        this.navigator = navigator;
        this.isExit = false;
    }

    @Override
    public void run() {
        while(!isExit) {
            navigator.printMenu();
        }
    }

    public void exitFromProgramm() {
        isExit = true;
    }
}
