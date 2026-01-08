import java.time.LocalTime;

public class SystemTimeDemonThread extends Thread{

    private int intervalTellsTime;
    private final int cntSecondsPerMinute = 1000;
  
    SystemTimeDemonThread(int intervalTellsTime) {
        this.intervalTellsTime = intervalTellsTime;
        setDaemon(true);
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                LocalTime time = LocalTime.now();
                System.out.print("Текущее системное время: ");
                System.out.print(time.getHour() + ":");
                System.out.print(time.getMinute() + ":");
                System.out.println(time.getSecond());
                sleep(cntSecondsPerMinute * intervalTellsTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public int getIntervalTellsTime() {
        return intervalTellsTime;
    }

}