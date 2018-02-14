import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

class OpenAqMain {

    static final String USER_AGENT = "Mozilla/5.0";
    static final String WS_END_POINT = "https://api.openaq.org/v1/cities?country=US";
    static final int HTTP_OK = 200;

    public static void main(String[] args) throws IOException {
        // make the connection
        URL url = new URL(WS_END_POINT);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("GET"); // optional since defaults to GET anyway...
        httpConn.setRequestProperty("User-Agent", USER_AGENT);
        httpConn.setRequestProperty("Accept", "application/json");

        // send the request
        System.out.println("Sending 'GET' request to web service openaq end-point " + WS_END_POINT);
        int responseCode = httpConn.getResponseCode();
        System.out.println("Response Code : " + responseCode);
        if (responseCode == HTTP_OK) {
            Scanner sc = new Scanner(httpConn.getInputStream());
            String str = "";
            while (sc.hasNext()) {
                String line = sc.nextLine().replace("\n", "");
                str += line;
            }
            sc.close();
            Gson gson = new Gson();
            Response response = gson.fromJson(str, Response.class);
            for (Result result : response.getResults()) {
                System.out.println("Country: " + result.getCountry());
                System.out.println("City: " + result.getCity());
                System.out.println("Locations: " + result.getLocations());
                System.out.println("Count: " + result.getCount());
                System.out.println();
            }
        }
    }
}