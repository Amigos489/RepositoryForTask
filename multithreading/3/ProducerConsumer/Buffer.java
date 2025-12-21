import java.util.Queue;
import java.util.LinkedList;

public class Buffer {
    private final int capacity;
    private final Queue<Integer> queue = new LinkedList<>();

    Buffer(int capacity) {
        this.capacity = capacity;
    }

    /* Положить в буфер */
    public synchronized void put(int num) throws InterruptedException {

        while (queue.size() == capacity) {
            System.out.println("Буфер полон, производитель ждёт.");
            wait();
        }

        queue.add(num);
        System.out.println("Производитель добавил в буфер: " + num);

        notifyAll();
    }

    /* Взять из буфера */
    public synchronized void take() throws InterruptedException {
        while (queue.size() == 0) {
            System.out.println("Буфер пуст, потребитель ждёт.");
            wait();
        }

        int num = queue.poll();
        System.out.println("Потребитель взял из буфера: " + num);

        notifyAll();
    }
}