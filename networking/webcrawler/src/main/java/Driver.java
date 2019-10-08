
import controller.Crawler;
import model.data.SecretKeys;

/**
 * A driver for the webcrawler. Checks for valid input then 
 * creates and passes control to a Crawler object.
 */
public class Driver {

    /**
     * The main() method of the Driver.
     * 
     * @param args : Two strings, a username and a password.
     */
    public static void main(String[] args) {
        // validate input
        if (args.length != 2) {
            System.err.println("Usage: ./webcrawler [username] [password]");
            System.exit(1);
        }

        // Set username and password strings to arguments ingested from command.
        String username = args[0];
        String password = args[1];

        // Start crawling Fakebook and get any discovered keys.
        SecretKeys keys = new Crawler(username, password).crawl();

        // print keys
        if (keys != null) {
            System.out.println(keys.toString());
        } else {
            throw new IllegalStateException("Keys returned null from Crawler.");
        }
    }
}