import java.io.IOException;
import java.net.*;

class MCastExample {
    static final int UCAST_TCP_PORT = 60000;

    public static void main(String[] args) throws IOException {
        // starts mcast receiver
        MCastReceiver mcastReceiver = new MCastReceiver();
        new Thread(mcastReceiver).start();

        // wait for 3 x MCastSender.MCAST_CYCLE
        long start = System.currentTimeMillis();
        boolean announReceived = false;
        while (true) {
            if (mcastReceiver.getSocketAddress() != null) {
                announReceived = true;
                break;
            }
            long now = System.currentTimeMillis();
            if (now - start > MCastSender.MCAST_CYCLE * 3)
                break;
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException ex) {
                System.out.println(ex);
                System.exit(1);
            }
        }
        if (announReceived)
            System.out.println("Resuming to client role!");
        else {
            System.out.println("Assuming server role!");
            SocketAddress socketAddress = new InetSocketAddress(InetAddress.getLocalHost(), UCAST_TCP_PORT);
            MCastSender mCastSender = new MCastSender(socketAddress);
            new Thread(mCastSender).start();
        }
    }
}
