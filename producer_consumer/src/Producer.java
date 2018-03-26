public class Producer implements Runnable {

    TaskManager taskManager;
    int numberTasks;

    Producer(TaskManager taskManager, int numberTasks) {
        this.taskManager = taskManager;
        this.numberTasks = numberTasks;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < numberTasks; i++) {
                Task task = new Task("Task #" + (i + 1));
                taskManager.put(task);
                System.out.println("Producer created " + task);
            }
        }
        catch (InterruptedException e) {

        }
    }
}
