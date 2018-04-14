import javax.swing.*;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

public class MultithreadedBenchmark implements Runnable {

    static final int SIZE = 40; // in bits but a multiple of 8 to simplify
    static final int NUMBER_CONSUMER_THREADS = 4;
    static final int QUEUE_CAPACITY = 10;
    private byte bytes[];
    private int size;
    private BlockingQueue<Task> queue;
    private PrintStream out;

    MultithreadedBenchmark(BlockingQueue<Task> queue, PrintStream out) {
        this.queue = queue;
        this.out = out;
    }

    void generate(byte bytes[], int size, PrintStream out) {
        if (size == SIZE / 8)
            //out.println(Thread.currentThread().getName() + " -> " + Arrays.toString(bytes));
            return;
        else {
            byte i = Byte.MIN_VALUE;
            while (true) {
                bytes[size] = i;
                generate(bytes, size + 1, out);
                if (i == Byte.MAX_VALUE)
                    break;
                i++;
            }
        }
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " started");
        while (true) {
            try {
                if (queue.isEmpty())
                    break;
                Task task = queue.take();
                byte bytes[] = task.getBytes();
                int size = task.getSize();
                generate(bytes, size, out);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        // start a producer thread
        Producer producer = new Producer(QUEUE_CAPACITY);
        BlockingQueue<Task> queue = producer.getQueue();
        new Thread(producer).start();

        // wait time to produce enough tasks
        System.out.println("Hit ENTER when ready!");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();

        // start consumer threads
        long startTime = System.nanoTime();
        Thread consumerThreads[] = new Thread[NUMBER_CONSUMER_THREADS];
        for (int i = 0; i < NUMBER_CONSUMER_THREADS; i++) {
            MultithreadedBenchmark multithreadedBenchmark = new MultithreadedBenchmark(queue, System.out);
            consumerThreads[i] = new Thread(multithreadedBenchmark);
            consumerThreads[i].setName("Consumer thread #" + i);
            consumerThreads[i].start();
        }

        // wait for all consumer threads to finish
        System.out.println("Waiting for consumer threads to end...");
        for (int i = 0; i < NUMBER_CONSUMER_THREADS; i++)
            consumerThreads[i].join();

        // log elapsed time
        long seconds = TimeUnit.SECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
        long minutes = TimeUnit.MINUTES.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
        System.out.println("It took " + seconds + "s or " + minutes + "m");
    }

}
