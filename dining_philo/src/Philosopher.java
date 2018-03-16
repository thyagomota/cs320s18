public class Philosopher implements Runnable {

    protected Object leftFork, rightFork;

    Philosopher(Object leftFork, Object rightFork) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    void think() {
        try {
            Thread.sleep((int) Math.random() * 10);
        }
        catch (InterruptedException ex) {}
    }

    void eat() {
        try {
            Thread.sleep((int) Math.random() * 50);
        }
        catch (InterruptedException ex) {}
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        while (true) {
            System.out.println(name + " is thinking...");
            think();
            synchronized (leftFork) {
                System.out.println(name + " got the left fork");
                synchronized (rightFork) {
                    System.out.println(name + " got the right fork");
                    System.out.println(name + " is eating...");
                    eat();
                }
                System.out.println(name + " released the right fork");
            }
            System.out.println(name + " released the left fork");
        }
    }
}
