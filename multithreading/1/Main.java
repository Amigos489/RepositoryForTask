public class Main {

    private static final Object lock = new Object();

    public static void main(String[] args) throws Exception {

        StateThread stateThread = new StateThread(lock);
        System.out.println(stateThread.getState());

        synchronized (lock) {
            stateThread.start();
            System.out.println(stateThread.getState());

            Thread.sleep(50);
            System.out.println(stateThread.getState());
        }

        Thread.sleep(100);
        System.out.println(stateThread.getState());

        Thread.sleep(600);
        System.out.println(stateThread.getState());

        synchronized (lock) {
            lock.notify();
        }

        stateThread.join();
        System.out.println(stateThread.getState());
    }
}
