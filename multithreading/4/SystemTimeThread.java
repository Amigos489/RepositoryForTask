import java.time.LocalTime;

public class SystemTimeThread extends Thread{

    private int n;
  
    SystemTimeThread(int n) {
        this.n = n;
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
                sleep(1000 * n);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}