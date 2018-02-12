import java.util.Arrays;

public class Contents {

    Quote[] quotes;
    String copyright;

    public Quote[] getQuotes() {
        return quotes;
    }

    @Override
    public String toString() {
        return "Contents{" +
                "quotes=" + Arrays.toString(quotes) +
                ", copyright='" + copyright + '\'' +
                '}';
    }
}
