import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

    private BlockingQueue<Task> queue;

    Producer(int capacity) {
        queue = new ArrayBlockingQueue<>(capacity);
    }

    BlockingQueue<Task> getQueue() {
        return queue;
    }

    @Override
    public void run() {
        byte i = Byte.MIN_VALUE;
        while (true) {
            byte bytes[] = new byte[MultithreadedBenchmark.SIZE / 8];
            bytes[0] = i;
            Task task = new Task(bytes, 1);
            //System.out.println(task);
            try {
                queue.put(task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (i == Byte.MAX_VALUE)
                break;
            i++;
        }
        System.out.println("Producer is done!");
    }
}
