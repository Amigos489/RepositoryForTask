public interface Menu {

    public void showMenu();
    public default void printMessage(String message) {

        System.out.println(message);

    }

}