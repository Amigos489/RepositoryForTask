public class Main {

    public static void main(String[] args) {
        ViewMenu viewMenu = new ViewMenu();
        ControllerMenu controllerMenu = new ControllerMenu(viewMenu);
        controllerMenu.startMenu();
    }

}