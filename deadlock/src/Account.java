public class Account {

    int balance;

    Account(int balance) {
        this.balance = balance;
    }

    Account() {
        this(0);
    }

    void withdraw(int amount) {
        this.balance -= amount;
        try {
            Thread.sleep(500);
        }
        catch (InterruptedException ex) {

        }
    }

    void deposit(int amount) {
        this.balance += amount;
    }

    void transfer(Account to, int amount) {
        Thread thread = Thread.currentThread();
        synchronized (this) {
            System.out.println("Thread " + thread.getName() + " trying to withdraw...");
            this.withdraw(amount);
            System.out.println("Thread " + thread.getName() + " done withdraw!");
            synchronized (to) {
                System.out.println("Thread " + thread.getName() + " trying to deposit...");
                to.deposit(amount);
                System.out.println("Thread " + thread.getName() + " done deposit!");
            }
        }
    }

    void transferDeadlockFree(Account to, int amount) {
        Thread thread = Thread.currentThread();
        synchronized (this) {
            System.out.println("Thread " + thread.getName() + " trying to withdraw...");
            this.withdraw(amount);
            System.out.println("Thread " + thread.getName() + " done withdraw!");
        }

        synchronized (to) {
            System.out.println("Thread " + thread.getName() + " trying to deposit...");
            to.deposit(amount);
            System.out.println("Thread " + thread.getName() + " done deposit!");
        }
    }

    @Override
    public String toString() {
        return "balance=" + balance;
    }

    public static void main(String[] args) {
        Account accountA = new Account(100);
        Account accountB = new Account(50);
        System.out.println("accountA " + accountA);
        System.out.println("accountB " + accountB);
        int amount = 10;
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread t1: accountA -> accountB");
                accountA.transfer(accountB, amount);
                //accountA.transferDeadlockFree(accountB, amount);
                System.out.println("Thread t1 is done!");
            }
        }
        );
        t1.setName("t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread t2: accountB -> accountA");
                accountB.transfer(accountA, amount);
                //accountB.transferDeadlockFree(accountA, amount);
                System.out.println("Thread t2 is done!");
            }
        }
        );
        t2.setName("t2");
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        }
        catch (InterruptedException ex) {
        }
        System.out.println("accountA " + accountA);
        System.out.println("accountB " + accountB);
    }
}
