/**
 * Created by hsenid on 6/7/17.
 */
public class Test extends Thread {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.setName("MenukaIsTheBest");
        myThread.start();
        System.out.println(myThread.getName());
        myThread.setName("Menuka");
        System.out.println("pi = " + myThread.pi);
    }

    @Override
    public void run() {

    }
}


class MyThread extends Thread {
    boolean negative = true;
    double pi; // Initializes to 0.0, by default

    public void run() {
        for (int i = 3; i < 100000; i += 2) {
            if (negative)
                pi -= (1.0 / i);
            else
                pi += (1.0 / i);
            negative = !negative;
        }
        pi += 1.0;
        pi *= 4.0;
        System.out.println("Finished calculating PI");

    }
}