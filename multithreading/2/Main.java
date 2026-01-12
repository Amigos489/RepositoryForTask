public class Main {

    public static void main(String[] args) throws InterruptedException {

        final Object lock = new Object();
        
        MyThread thread1 = new MyThread(0, lock);
        MyThread thread2 = new MyThread(1, lock);
        thread1.start();
        thread2.start();
    }
    
}