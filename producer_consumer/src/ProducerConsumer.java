public class ProducerConsumer {

    TaskManager taskManager;
    Thread producerThread, consumerThread;

    ProducerConsumer(int numberTasks) {
        taskManager = new TaskManager(5);
        producerThread = new Thread(new Producer(taskManager, numberTasks));
        consumerThread = new Thread(new Consumer(taskManager, numberTasks));
    }

    void run() throws InterruptedException {
        producerThread.start();
        consumerThread.start();
        consumerThread.join();
    }

    public static void main(String[] args) throws InterruptedException {
        new ProducerConsumer(10).run();
    }
}
