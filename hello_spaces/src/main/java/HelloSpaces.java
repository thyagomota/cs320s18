import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;

public class HelloSpaces {
    static final String SPACE_URL = "jini://gitkeeper.cs.moravian.edu/*/test";

    public static void main(String[] args) {
        GigaSpaceConfigurer conf = new GigaSpaceConfigurer(new UrlSpaceConfigurer(SPACE_URL));
        GigaSpace space = conf.create();

        // creating an object in the space
        Message msg = new Message();
        msg.setId(21);
        msg.setText("Hello");
        space.write(msg);

        // read the object
        msg = space.readById(Message.class, 20);
        System.out.println(msg);

    }
}
