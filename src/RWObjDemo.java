import java.io.*;

public class RWObjDemo {
    private FileInputStream fis;
    private FileOutputStream fos;
    private ObjectOutputStream oos;
    private ObjectInputStream oin;
    private static final String FILE = "prices";

    public void write(Flower...flowers) {
        try {   //try with res. - only java 9?
            fos = new FileOutputStream(FILE, true);
            oos = new ObjectOutputStream(fos);
            for(Flower f: flowers) {
                oos.writeObject(f);
            }
            oos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void read() {
        try {
            fis = new FileInputStream(FILE);
            oin = new ObjectInputStream(fis);
            Flower f = (Flower) oin.readObject();
            System.out.println("color: " + f.getColor() + ", name: " + f.getName() + ", price: " + f.getPrice());
            f = (Flower) oin.readObject();
            System.out.println("color: " + f.getColor() + ", name: " + f.getName() + ", price: " + f.getPrice());
            f = (Flower) oin.readObject();
            System.out.println("color: " + f.getColor() + ", name: " + f.getName() + ", price: " + f.getPrice());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e) {
            System.out.println("EOF");
        }catch (StreamCorruptedException e) {
            System.err.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                oin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
