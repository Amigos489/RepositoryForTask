/* Абстрактный класс меню */

public abstract class MenuView {

    public abstract void showMenu(); /* Отобразить меню */

    /* Вывод сообщения о результате выбора */
    public void printResultChoice(String choice) {
        System.out.println("Выбор" + choice);
    }

    /* Вывод сообщения об ошибке */
    public void printErrorMessage(String messageError) {
        System.out.println("Ошибка! " + messageError);
    }

}