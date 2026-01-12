import java.util.Random;

public class Producer extends Thread {

    private Buffer buffer;
    private Random random = new Random();

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }
    
    @Override
    public void run() {
        try {
            for (int i = 0; i < 20; ++i) {
                int num = random.nextInt(100);
                buffer.put(num);
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}