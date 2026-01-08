public class MainThread extends Thread {

    private int cntTellsTime;
    private final int cntSecondsPerMinute = 1000;

    public MainThread(int cntTellsTime) {
        this.cntTellsTime = cntTellsTime;
    }

    @Override
    public void run() {
        System.out.println("Начало работы главного потока.");

        SystemTimeDemonThread systemTimeDemonThread = new SystemTimeDemonThread(5);
        systemTimeDemonThread.start();
        try {
            Thread.sleep(systemTimeDemonThread.getIntervalTellsTime() * cntSecondsPerMinute * cntTellsTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Прекращение работы главного потока.");
    }
    
}
