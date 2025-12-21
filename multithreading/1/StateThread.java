public class StateThread extends Thread {

    private final Object lock;

    StateThread(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
       synchronized (lock) {
            try {
                Thread.sleep(500);
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}