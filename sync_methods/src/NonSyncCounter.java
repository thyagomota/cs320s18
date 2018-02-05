class NonSyncCounter extends Counter {

    private int c = 0;

    void increment() {
        c++;
    }

    void decrement() {
        c--;
    }

    int getValue() {
        return c;
    }
}
