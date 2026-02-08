package ui;

public interface Navigator {
    int printMenu();
    void navigate(int index);
    int getCountItemInMenu();
    void backToMainMenu();
}
