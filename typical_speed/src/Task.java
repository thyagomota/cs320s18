import java.io.Serializable;
import java.util.Arrays;

public class Task {

    private byte bytes[];
    private int size;

    public Task(byte[] bytes, int size) {
        this.bytes = bytes;
        this.size = size;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return Arrays.toString(bytes) + ", size=" + size;
    }
}
