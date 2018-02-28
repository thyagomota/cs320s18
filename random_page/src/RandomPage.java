import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

class RandomPage implements Runnable {

    private static final String RANDOM_WEB_SERVICE_URL = "http://randomweb.site/get.php";
    private static final int    SECONDS_BTW_PAGES = 3;
    private boolean done;

    RandomPage() {
        done = false;
    }

    synchronized boolean isDone() {
        return done;
    }

    synchronized void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public void run() {
        try {
            // make the connection
            URL url = new URL(RANDOM_WEB_SERVICE_URL);
            while (!isDone()) {
                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                String randomPage = new Scanner(httpConn.getInputStream()).nextLine();
                System.out.println(randomPage);
                Thread.sleep(SECONDS_BTW_PAGES * 1000);
            }
        } catch (Exception e) {
        }
        System.out.println("Random page thread is done now!");
    }

    public static void main(String[] args) {
        System.out.println("Here are some random pages I found on the web...");
        System.out.println("Just hit ENTER when you get tired of that!");

        // start a new thread to get random pages
        RandomPage randomPage = new RandomPage();
        new Thread(randomPage).start();

        // block waiting for user to get tired of that...
        new Scanner(System.in).hasNextLine();

        // finish the thread first
        randomPage.setDone(true);
        System.out.println("Main thread is over!");
    }
}
