import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;

public class RWText {
    private static final String XML_FILE = "prices.xml";
    private static final String JSON_FILE = "prices.json";
    private static final String NAME_PAR_XML = "flowername=\"";
    private static final String COLOR_PAR_XML = "\" color=\"";
    private static final String PRICE_PAR_XML = "\">";
    private static final String NAME_PAR_JSON = "\t\t\"name\": \"";
    private static final String COLOR_PAR_JSON = "\t\t\"color\": \"";
    private static final String PRICE_PAR_JSON = "\t\t\"price\": \"";
    private Flower[] flowers;
    private String name, color;
    private double price;

    public void write(Flower... flowers) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(XML_FILE, false))) {
            pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
            pw.println("<!DOCTYPE prices>");
            pw.println("<prices>");
            for (Flower f : flowers) {
                pw.print("\t<price " + NAME_PAR_XML + f.getName() + COLOR_PAR_XML + f.getColor() + "\">");
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
                    pw.println(NAME_PAR_JSON + flowers[i].getName() + "\",");
                    pw.println(COLOR_PAR_JSON + flowers[i].getColor() + "\",");
                    pw.println(PRICE_PAR_JSON + flowers[i].getPrice() + "\"");
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
                String s = lines.get(i);
                if(s.contains(NAME_PAR_XML)) {
                    name = s.substring((s.indexOf(NAME_PAR_XML) + NAME_PAR_XML.length()), s.indexOf(COLOR_PAR_XML));
                    color = s.substring((s.indexOf(COLOR_PAR_XML) + COLOR_PAR_XML.length()), s.indexOf(PRICE_PAR_XML));
                    String p = s.substring((s.indexOf(PRICE_PAR_XML) + PRICE_PAR_XML.length()), s.indexOf("</price>"));
                    price = Double.parseDouble(p);
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
                    if(s.contains(NAME_PAR_JSON)) {
                        countFlowers++;
                    }
                }
                if (countFlowers == 0) {
                    System.out.println("No data!");
                    return;
                }
                flowers = new Flower[countFlowers];
                for(int i=0, j=0; i<lines.size(); i++) {
                    String s = lines.get(i);
                    if(s.contains(NAME_PAR_JSON)) {
                        name = s.substring(NAME_PAR_JSON.length(), s.length()-2);
                    }
                    if(s.contains(COLOR_PAR_JSON)) {
                        color = s.substring(COLOR_PAR_JSON.length(), s.length()-2);
                    }
                    if(s.contains(PRICE_PAR_JSON)) {
                        price = Double.parseDouble(s.substring(PRICE_PAR_JSON.length(), s.length()-1));
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
        double p = -1;
        for(Flower f: flowers) {
            if(f.getName().equals(name) && f.getColor().equals(color)) {
                p = f.getPrice();
            }
        }
        if (p == -1) {
            System.out.print("flower not found, ");
        }
        return p;
    }
}
