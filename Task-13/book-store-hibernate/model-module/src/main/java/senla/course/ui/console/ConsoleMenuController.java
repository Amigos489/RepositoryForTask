package senla.course.ui.console;

import senla.course.ui.Builder;
import senla.course.ui.MenuController;
import senla.course.ui.Navigator;

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

    @Override
    public void exitFromProgramm() {
        isExit = true;
    }
}
