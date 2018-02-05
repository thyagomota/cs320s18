import java.util.Random;

class ThreadsSimulation implements Runnable {

    Counter counter;
    Random random;

    ThreadsSimulation(final Counter counter) {
        this.counter = counter;
        random = new Random();
    }

    @Override
    public void run() {
        for (int i = 1; i <= 100; i++) {
            if (i % 2 == 0)
                counter.increment();
            else
                counter.decrement();
            try {
                Thread.sleep(random.nextInt(100));
            }
            catch (InterruptedException ex) {

            }
        }
    }

    public static void main(String[] args) {
        //Counter counter = new NonSyncCounter();
        Counter counter = new SyncCounter();
        Thread t1 = new Thread(new ThreadsSimulation(counter));
        Thread t2 = new Thread(new ThreadsSimulation(counter));
        t1.start();
        t2.start();
        while (t1.isAlive() || t2.isAlive())
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException ex) {

            }
        System.out.println("Value of counter is " + counter.getValue());
    }
}
