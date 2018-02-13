import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class RWTextDemo {
    private FileInputStream fis;
    //private FileOutputStream fos;
    private static final String XML_FILE = "prices.xml";
    private static final String JSON_FILE = "prices.json";

    public void write(Flower...f) {
        try (FileOutputStream fos = new FileOutputStream(XML_FILE, true)) {
            //fos = new FileOutputStream(XML_FILE, true);
            //fos.
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
