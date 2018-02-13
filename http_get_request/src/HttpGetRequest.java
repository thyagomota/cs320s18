import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

class HttpGetRequest {

    //static final String USER_AGENT = "Mozilla/5.0";
    static final String USER_AGENT = "Mozilla/5.0 (X11; U; Linux i586; en-US; rv:1.7.3) Gecko/20040924 Epiphany/1.4.4 (Ubuntu)";

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Use: java HttpGetRequest <URL>");
            System.exit(1);
        }
        String strUrl = args[0];
        System.out.println("URL: " + strUrl);

        // make the connection
        URL url = new URL(strUrl);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("GET"); // optional since defaults to GET anyway...
        httpConn.setRequestProperty("User-Agent", USER_AGENT);

        // send the request
        System.out.println("Sending 'GET' request to URL : " + url);
        int responseCode = httpConn.getResponseCode();
        System.out.println("Response Code : " + responseCode);
        // check for redirect...
        if (responseCode == 301) {
            String newUrl = httpConn.getHeaderField("Location");
            System.out.println("Redirect: " + newUrl);
        }
        Scanner sc = new Scanner(httpConn.getInputStream());
        while (sc.hasNext()) {
            String line = sc.nextLine();
            System.out.println(line);
        }
        sc.close();
    }
}
