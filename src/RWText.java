import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;

public class RWText {
    private static final String XML_FILE = "prices.xml";
    private static final String JSON_FILE = "prices.json";
    private static final int NAME_START_XML = 20;
    private static final int COLOR_DELTA_XML = 7;
    private static final int PRICE_DELTA_XML = 2;
    private static final int NAME_START_JSON = 17;
    private static final int COLOR_START_JSON = 12;
    private static final int PRICE_START_JSON = COLOR_START_JSON;
    private Flower[] flowers;
    private String name, color;
    private double price;

    public void write(Flower... flowers) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(XML_FILE, false))) {
            pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
            pw.println("<!DOCTYPE prices>");
            pw.println("<prices>");
            for (Flower f : flowers) {
                pw.print("\t<price flowername=\"" + f.getName() + "\" color=\"" + f.getColor() + "\">");
                pw.println(f.getPrice() + "</price>");
            }
            pw.println("</prices>");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(boolean writeXML, Flower... flowers) {
        if (writeXML) {
            write(flowers);
        } else {
            try (PrintWriter pw = new PrintWriter(new FileWriter(JSON_FILE, false))) {
                pw.println("{");
                for (int i = 0; i < flowers.length; i++) {
                    pw.println("\t\"flower\": {");
                    pw.println("\t\t\"flowername\": \"" + flowers[i].getName() + "\",");
                    pw.println("\t\t\"color\": \"" + flowers[i].getColor() + "\",");
                    pw.println("\t\t\"price\": \"" + flowers[i].getPrice() + "\"");
                    if (i == flowers.length - 1) {
                        pw.println("\t}");
                    } else {
                        pw.println("\t},");
                    }
                }
                pw.println("}");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void read() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(XML_FILE), StandardCharsets.UTF_8);
            if (lines.size()>4) {
                flowers = new Flower[lines.size() - 4];
            } else {
                System.out.println("No data!");
                return;
            }
            for(int i=0, j=0; i<lines.size(); i++){
                if(lines.get(i).contains("flowername")) {
                    name = lines.get(i).substring(NAME_START_XML, lines.get(i).indexOf("\" color"));
                    color = lines.get(i).substring((lines.get(i).indexOf("color") + COLOR_DELTA_XML), lines.get(i).indexOf("\">"));
                    String s = lines.get(i).substring((lines.get(i).indexOf("\">") + PRICE_DELTA_XML), lines.get(i).indexOf("</price>"));
                    price = Double.parseDouble(s);
                    flowers[j++] = new Flower(name, color, price);
                }
            }
        } catch (NoSuchFileException nfe) {
            System.out.println(nfe);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read(boolean readXML) {
        if (readXML) {
            read();
        } else {
            int countFlowers = 0;
            try {
                List<String> lines = Files.readAllLines(Paths.get(JSON_FILE), StandardCharsets.UTF_8);
                for (String s: lines) {
                    if(s.contains("flowername")) {
                        countFlowers++;
                    }
                }
                if (countFlowers == 0) {
                    System.out.println("No data!");
                    return;
                }
                flowers = new Flower[countFlowers];
                for(int i=0, j=0; i<lines.size(); i++) {
                    if(lines.get(i).contains("flowername")) {
                        name = lines.get(i).substring(NAME_START_JSON, lines.get(i).indexOf("\","));
                    }
                    if(lines.get(i).contains("color")) {
                        color = lines.get(i).substring(COLOR_START_JSON, lines.get(i).indexOf("\","));
                    }
                    if(lines.get(i).contains("price")) {
                        price = Double.parseDouble(lines.get(i).substring(PRICE_START_JSON, lines.get(i).length()-1));
                        flowers[j++] = new Flower(name, color, price);
                    }
                }
            } catch (NoSuchFileException nfe) {
                System.out.println(nfe);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public double getPrice(String name, String color) {
        if (flowers==null || flowers.length==0 || flowers[0]==null) {
            System.out.println("Array not initialized!");
            return 0;
        }
        double p = 0;
        for(Flower f: flowers) {
            if(f.getName().equals(name) && f.getColor().equals(color)) {
                p = f.getPrice();
            }
        }
        if (p ==0) {
            System.out.print("flower not found, ");
        }
        return p;
    }
}
