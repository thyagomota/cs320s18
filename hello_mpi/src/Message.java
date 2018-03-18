import java.io.Serializable;

public class Message implements Serializable {

    private int senderRank;
    private String msg;

    Message(int senderRank, String msg) {
        this.senderRank = senderRank;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Message{" +
                "senderRank=" + senderRank +
                ", msg='" + msg + '\'' +
                '}';
    }


}
