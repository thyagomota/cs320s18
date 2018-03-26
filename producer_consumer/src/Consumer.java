public class Consumer implements Runnable {

    TaskManager taskManager;
    int numberTasks;

    Consumer(TaskManager taskManager, int numberTasks) {
        this.taskManager = taskManager;
        this.numberTasks = numberTasks;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < numberTasks; i++) {
                Task task = taskManager.take();
                System.out.println("Consumer is working on " + task);
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ex) {

                }
            }
        }
        catch (InterruptedException e) {

        }
    }
}
