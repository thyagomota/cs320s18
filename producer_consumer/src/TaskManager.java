import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TaskManager {

    private BlockingQueue<Task> tasks;
    private static final int DEFAULT_CAPACITY = 10;

    TaskManager(int capacity) {
        tasks = new ArrayBlockingQueue<>(capacity);
    }

    TaskManager() {
        this(DEFAULT_CAPACITY);
    }

    boolean add(Task task) {
        try {
            return tasks.add(task);
        }
        catch (IllegalStateException e) {
            return false;
        }
    }

    void put(Task task) throws InterruptedException {
        tasks.put(task);
    }

    Task remove() {
        try {
            return tasks.remove();
        }
        catch (NoSuchElementException e) {
            return null;
        }
    }

    Task take() throws InterruptedException {
        return tasks.take();
    }

    boolean isEmpty() {
        return tasks.isEmpty();
    }
}
