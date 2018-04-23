import org.omg.CORBA.Any;

import java.net.InetSocketAddress;

/**
 * CSCI320 - Networking & Distributed Computing
 * Spring 2018
 * Defines a multicast agent with a multicast socket address.
 * @author Thyago Mota
 */
abstract class MCastAgent extends AnyCastAgent implements Runnable {

    protected InetSocketAddress mcastSocketAddress;

    MCastAgent(InetSocketAddress mcastSocketAddress) {
        this.mcastSocketAddress = mcastSocketAddress;
    }

    InetSocketAddress getMCastSocketAddress() {
        return mcastSocketAddress;
    }
}
