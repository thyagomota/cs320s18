import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        String host = "localhost";
        if (args.length != 0)
            host = args[0];
        System.out.println("Using host " + host);
        Registry registry = LocateRegistry.getRegistry(host);
        Hello stub = (Hello) registry.lookup("Hello");
        Scanner sc = new Scanner(System.in);
        System.out.println("Name? ");
        String name = sc.nextLine();
        // System.out.println(stub.sayHello());
        System.out.println(stub.sayHello(name));
    }
}
