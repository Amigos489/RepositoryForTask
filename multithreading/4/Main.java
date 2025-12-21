public class Main {
    public static void main(String[] args) {
        SystemTimeThread timeThread = new SystemTimeThread(7);
        timeThread.start();
    }
}