import java.util.List;

public class Response {

    Metadata meta;
    List<Result> results;

    public Metadata getMeta() {
        return meta;
    }

    public List<Result> getResults() {
        return results;
    }
}