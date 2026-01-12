public class Main {

    public static void main(String[] args) {
        Buffer buffer = new Buffer(10);
        Consumer consumer = new Consumer(buffer);
        Producer producer = new Producer(buffer);

        consumer.start();
        producer.start();
    }
    
}
