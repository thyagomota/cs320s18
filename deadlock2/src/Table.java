public class Table {

    private Object knife;
    private Object butter;

    Table() {
        knife = new MyObject();
        butter = new MyObject();
    }

    Object getKnife() {
        return knife;
    }

    Object getButter() {
        return butter;
    }

    public static void main(String[] args) {
        Table table = new Table();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                // first get the knife
                synchronized (table.getKnife()) {
                    System.out.println("Thread " + name + " got the knife!");
                    // now think for 2 seconds...
                    System.out.println("Thread " + name + " is thinking...");
                    // now try to get the butter
                    synchronized (table.getButter()) {
                        System.out.println("Thread " + name + " got the butter!");
                    }
                    System.out.println("Thread " + name + " released the butter!");
                }
                System.out.println("Thread " + name + " released the knife!");
            }
        });
        t1.setName("t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                // first get the butter
                synchronized (table.getButter()) {
                    System.out.println("Thread " + name + " got the butter!");
                    // now think for 2 seconds...
                    System.out.println("Thread " + name + " is thinking...");
                    // now try to get the knife
                    synchronized (table.getKnife()) {
                        System.out.println("Thread " + name + " got the knife!");
                    }
                    System.out.println("Thread " + name + " released the knife!");
                }
                System.out.println("Thread " + name + " released the butter!");
            }
        });
        t2.setName("t2");

        t1.start();
        t2.start();
    }
}
