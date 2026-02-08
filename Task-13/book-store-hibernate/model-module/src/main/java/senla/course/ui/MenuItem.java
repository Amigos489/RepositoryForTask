package senla.course.ui;

public interface MenuItem {
    void doAction();

    String getTitle();

    void setTitle(String title);

    Menu getNextMenu();
}
