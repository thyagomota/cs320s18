import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;

public class Server implements Hello {
    @Override
    public String sayHello() throws RemoteException {
        return "Hello!";
    }

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        String host = "localhost";
        if (args.length != 0)
            host = args[0];
        Server obj = new Server();
        Hello skeleton = (Hello) UnicastRemoteObject.exportObject(obj, 0);
        Registry registry = LocateRegistry.getRegistry(host);
        registry.rebind("Hello", skeleton);
        System.out.println("Server ready!");
    }
}
