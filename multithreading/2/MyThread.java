public class MyThread extends Thread {

    private final int num;

    MyThread(int num) {
        this.num = num;
    }

    @Override
    public void run() {
        System.out.println("Привет! Я поток с номером " + num);
        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getNum() {
        return num;
    }

}