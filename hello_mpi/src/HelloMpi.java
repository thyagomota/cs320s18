import mpi.Comm;
import mpi.MPI;
import mpi.MPIException;

import java.io.*;

public class HelloMpi {

    private static final int TAG = 0;
    private static final int MAX_SIZE = 1024;

    byte[] serialize(Message msg) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(msg);
        return bos.toByteArray();
    }

    Message deserialize(byte bytes[]) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream in = new ObjectInputStream(bis);
        return (Message) in.readObject();
    }

    void run() throws MPIException, IOException, ClassNotFoundException {
        Comm comm = MPI.COMM_WORLD;
        int np = comm.getSize();
        int rank = comm.getRank();
        if (rank == 0)
            for (int i = 1; i < np; i++) {
                byte bytes[] = new byte[MAX_SIZE];
                comm.recv(bytes, MAX_SIZE, MPI.BYTE, i, TAG);
                Message msg = deserialize(bytes);
                System.out.println(msg);
            }
        else {
            Message msg = new Message(rank, "hello");
            byte bytes[] = serialize(msg);
            comm.send(bytes, bytes.length, MPI.BYTE, 0, TAG);
        }
    }

    public static void main(String[] args) throws MPIException, IOException, ClassNotFoundException {
        MPI.Init(args);
        new HelloMpi().run();
        MPI.Finalize();
    }
}
