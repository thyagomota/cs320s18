import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MyPingTest {

    static final int NUMBER_ARGS = 2;

    static void help() {
        System.out.println("Use: MyPingTest host ms");
    }

    public static void main(String[] args) throws IOException {
        if (args.length != NUMBER_ARGS)
            help();
        String host = args[0];
        int timeout = Integer.parseInt(args[1]);
        System.out.println("Pinging " + host + " with a timeout of " + timeout + "ms");
        InetAddress inetAddress = InetAddress.getByName(host);
        System.out.println("inetAddress is " + inetAddress);
        if (inetAddress.isReachable(timeout))
            System.out.println("success!");
        else
            System.out.println("unreachable!");
    }
}
