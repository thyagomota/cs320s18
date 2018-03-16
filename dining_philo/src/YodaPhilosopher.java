public class YodaPhilosopher extends Philosopher {

    YodaPhilosopher(Object leftFork, Object rightFork) {
        super(leftFork, rightFork);
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        while (true) {
            System.out.println(name + " is thinking...");
            think();
            synchronized (rightFork) {
                System.out.println(name + " got the right fork");
                synchronized (leftFork) {
                    System.out.println(name + " got the left fork");
                    System.out.println(name + " is eating...");
                    eat();
                }
                System.out.println(name + " released the left fork");
            }
            System.out.println(name + " released the right fork");
        }
    }
}
