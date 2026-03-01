package senla.course.ui;

import senla.course.exceptions.InvalidInput;

public interface Navigator {
    int printMenu();
    void navigate(int index);
    void backToMainMenu();

    int processingUserInput() throws InvalidInput;
    void setCurrentMenu(Menu currentMenu);
    void setMainMenu(Menu mainMenu);
}

