import javax.swing.*;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Benchmark implements Runnable {

    static final int SIZE = 40; // in bits but a multiple of 8 to simplify
    private byte bytes[];
    private int size;
    private PrintStream out;

    Benchmark(byte bytes[], int size, PrintStream out) {
        this.bytes = bytes;
        this.size = size;
        this.out = out;
    }

    void generate(byte bytes[], int size, PrintStream out) {
        if (size == SIZE / 8)
            return;
            //out.println(Arrays.toString(bytes));
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
        generate(bytes, size, out);
    }

    public static void main(String[] args) throws InterruptedException {
        byte bytes[] = new byte[SIZE / 8];
        long startTime = System.nanoTime();
        Benchmark benchmark = new Benchmark(bytes, 0, System.out);
        Thread thread = new Thread(benchmark);
        thread.start();
        thread.join();
        long seconds = TimeUnit.SECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
        System.out.println("It took " + seconds + "s");
    }

}
