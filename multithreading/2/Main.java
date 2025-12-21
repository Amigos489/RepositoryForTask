public class Main {

    public static void main(String[] args) throws InterruptedException {
        for(int i = 0; i < 100; i++) {
            MyThread thread1 = new MyThread(1);
            MyThread thread2 = new MyThread(2);
            thread1.start();
            thread1.join();
            thread2.start();
            thread2.join();
        }

    }
    
}