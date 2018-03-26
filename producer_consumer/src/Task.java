import java.io.*;

public class Task implements Serializable {

    private int id;
    static  int nextId = 1;
    private String description;

    public Task() {
        this.id = 0;
        this.description = "";
    }

    public Task(String description) {
        this.id = nextId++;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }

    byte[] serialize() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(this);
        return bos.toByteArray();
    }

    static Task deserialize(byte bytes[]) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream in = new ObjectInputStream(bis);
        return (Task) in.readObject();
    }
}
