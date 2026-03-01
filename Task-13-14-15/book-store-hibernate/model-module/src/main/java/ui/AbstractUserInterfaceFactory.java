package ui;

public interface AbstractUserInterfaceFactory {
    public Menu makeMenu();
    public MenuItem makeMenuItem();
    public Navigator makeNavigator();
    public MenuController makeMenuController();
}
