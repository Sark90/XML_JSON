import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Main {
    public static void main(String[] args) {
        Flower rose1 = new Flower("rose", "red", 40);
        Flower rose2 = new Flower("rose", "blue", 100);
        Flower pink = new Flower("pink", "purple", 18);
        Flower tulip = new Flower("tulip", "yellow", 23);

        RWText rwText = new RWText();
        rwText.write(tulip, rose1);
        rwText.write(false, pink, rose2);

        rwText.read();
        System.out.println("yellow tulip: " + rwText.getPrice("tulip", "yellow"));
        rwText.read(false);
        System.out.println("blue rose: " + rwText.getPrice("rose", "blue"));

        /*try {
            rose1.writeExternal(new ObjectOutputStream(new FileOutputStream("file")));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}