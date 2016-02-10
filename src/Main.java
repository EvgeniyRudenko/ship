import geometry.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<PortalCrane> portalCranes = new ArrayList<>();
        ArrayList<Facility> facilities = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Oleks\\Desktop\\data.txt"));
        while (reader.ready()) {
            String[] s = reader.readLine().trim().replaceAll("\\s+", " ").split(" ");
            if (s.length == 4) {
                //System.out.println(Arrays.toString(s));
                double[] d = convertStringArrayToDoubleArray(s);
                portalCranes.add(new PortalCrane(new Point(d[0], d[1]), new Point(d[2], d[3])));
            } else if (s.length == 8) {
                //System.out.println(Arrays.toString(s));
                double[] d = convertStringArrayToDoubleArray(s);
                facilities.add(new ShipCrane
                        (new Point(d[0], d[1]), new Point(d[2], d[3]),
                                new Point(d[4], d[5]), new Point(d[6], d[7])));
            }
        }
        reader.close();

        FileWriter writer = new FileWriter("C:\\Users\\Oleks\\Desktop\\result.txt");
        for (PortalCrane portalCrane : portalCranes) {
            //System.out.println(portalCrane);
            writer.write(portalCrane + System.lineSeparator() + System.lineSeparator());
        }

        for (Facility facility : facilities) {
            //System.out.println(facility);
            writer.write(facility + System.lineSeparator() + System.lineSeparator());
        }


        for (int i = 0; i < portalCranes.size() - 1; i++) {
            writer.write(portalCranes.get(i).crash(portalCranes.get(i + 1)) +
                    System.lineSeparator() + System.lineSeparator());
        }
        for (int i = portalCranes.size() - 1; i > 0; i--) {
            writer.write(portalCranes.get(i).crash(portalCranes.get(i - 1)) +
                    System.lineSeparator() + System.lineSeparator());
        }

        writer.close();
    }

    public static double[] convertStringArrayToDoubleArray(String[] s) {
        double[] d = new double[s.length];
        for (int i = 0; i < s.length; i++) {
            d[i] = Double.parseDouble(s[i]);
        }
        return d;
    }
}


