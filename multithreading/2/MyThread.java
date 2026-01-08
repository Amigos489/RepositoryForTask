public class MyThread extends Thread {

    private final int numThread;
    private final Object lock;

    MyThread(int numThread, Object lock) {
        this.numThread = numThread;
        this.lock = lock;
    }

    @Override
    public void run() {
        while(true) {
            synchronized(lock) {
                System.out.println("Привет! Я поток с номером " + numThread);
                try {
                    sleep(500);
                    lock.notify();
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int getNumThread() {
        return numThread;
    }

}