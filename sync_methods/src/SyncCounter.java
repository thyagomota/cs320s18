class SyncCounter extends Counter {

    private int c = 0;

    synchronized void increment() {
        c++;
    }

    synchronized void decrement() {
        c--;
    }

    synchronized int getValue() {
        return c;
    }
}
