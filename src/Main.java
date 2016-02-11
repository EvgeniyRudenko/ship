import geometry.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ArrayList<PortalCrane> portalCranes = new ArrayList<>();
        ArrayList<Facility> facilities = new ArrayList<>();
        ArrayList<String> classes = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("data.txt"));
        while (reader.ready()) {
            String line = reader.readLine();
            if (line.equals("")) continue;
            String[] s = line.trim().replaceAll("\\s+", " ").split(" ");
            Class clazz;
            try {
                clazz = Class.forName(s[0].substring(0, s[0].length() - 1));
                classes.add(clazz.getName());
            } catch (ClassNotFoundException e) {
                double[] d = convertStringArrayToDoubleArray(s);
                if (s.length == 4) {
                    portalCranes.add(new PortalCrane(new Point(d[0], d[1]), new Point(d[2], d[3])));
                } else if (s.length == 8) {
                    Point[] points = new  Point[]{new Point(d[0], d[1]), new Point(d[2], d[3]),
                                                  new Point(d[4], d[5]), new Point(d[6], d[7])};
                    if (classes.get(classes.size()-1).equals("ShipCrane"))
                        facilities.add(new ShipCrane(points));
                    if (classes.get(classes.size()-1).equals("Storage"))
                        facilities.add(new Storage(points));
                }
            }
        }
        reader.close();

        //FileWriter writer = new FileWriter("C:\\Users\\Oleks\\Desktop\\result.txt");
        FileWriter writer = new FileWriter("result.txt");
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


