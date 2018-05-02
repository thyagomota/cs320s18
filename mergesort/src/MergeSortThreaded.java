import java.util.Arrays;
import java.util.Random;

public class MergeSortThreaded implements Runnable {

    int data[];

    MergeSortThreaded(int size) {
        Random r = new Random();
        data = new int[size];
        for (int i = 0; i < size; i++)
            data[i] = r.nextInt(size);
    }

    void merge(int begin, int middle, int end) {
        int mergeSize = end - begin + 1;
        int temp[] = new int[mergeSize];
        int k = 0;
        int i = begin;
        int j = middle + 1;
        while (i < middle + 1 && j <= end)
            if (data[i] < data[j])
                temp[k++] = data[i++];
            else
                temp[k++] = data[j++];
        while (i < middle + 1)
            temp[k++] = data[i++];
        while (j <= end)
            temp[k++] = data[j++];
        System.arraycopy(temp, 0, data, begin, mergeSize);
    }

    void sort(int begin, int end) {
        if (begin >= end)
            return;
        int middle = begin + (end - begin) / 2;
        sort(begin, middle);
        sort(middle + 1, end);
        merge(begin, middle, end);
    }

    @Override
    public String toString() {
        return Arrays.toString(data);
    }

    @Override
    public void run() {
        sort(0, data.length - 1);
    }

    public static void main(String[] args) throws InterruptedException {
        MergeSortThreaded mergeSortThreaded = new MergeSortThreaded(30);
        System.out.println(mergeSortThreaded);
        Thread t = new Thread(mergeSortThreaded);
        t.start();
        t.join();
        System.out.println(mergeSortThreaded);
    }
}
