import java.rmi.*;

public interface Hello extends Remote {

    String sayHello() throws RemoteException;

    String sayHello(String name) throws RemoteException;
}
