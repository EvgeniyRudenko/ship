package main;

import geometry.Point;
import gui.MyPanel;
import objects.Facility;
import objects.PortalCrane;
import objects.ShipCrane;
import objects.Storage;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Main {

        public static void main(String[] args) throws
                IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
                InvocationTargetException, InstantiationException, InterruptedException {
        final ArrayList<PortalCrane> portalCranes = new ArrayList<>();
        ArrayList<Facility> facilities = new ArrayList<>();
        ArrayList<String> classes = new ArrayList<>();
//        BufferedReader reader = new BufferedReader(new FileReader(("data.txt")));
        BufferedReader reader = new BufferedReader(new InputStreamReader
                (new FileInputStream("./data.txt")));
        while (reader.ready()) {
            String line = reader.readLine();
            if (line.equals("")) continue;
            String[] s = line.trim().replaceAll("\\s+", " ").split(" ");
            Class clazz;
            try {
                clazz = Class.forName("objects." + s[0].substring(0, s[0].length() - 1));
                classes.add(clazz.getSimpleName());
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

        FileWriter writer = new FileWriter("result.txt");

        for (PortalCrane portalCrane : portalCranes) {
            writer.write(portalCrane + System.lineSeparator() + System.lineSeparator());
        }

        for (Facility facility : facilities) {
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

        for (int i = 0; i < portalCranes.size(); i++) {
            for (int j = 0; j < facilities.size(); j++) {
                writer.write(portalCranes.get(i).crash(facilities.get(j))+
                        System.lineSeparator()+System.lineSeparator());
            }
        }

        for (int i = 0; i < facilities.size(); i++) {
            for (int j = 0; j < portalCranes.size(); j++) {
                writer.write(facilities.get(i).crash(portalCranes.get(j))+
                        System.lineSeparator()+System.lineSeparator());
            }
        }

        writer.close();

            final JFrame frame = new JFrame("CraneSafety 1.0");
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            final MyPanel myPanel = new MyPanel(portalCranes, facilities);
            SpinnerModel sm = new SpinnerNumberModel(1, 1, 10, 1);//default value,lower bound,upper bound,increment by
            final JSpinner spinner = new JSpinner(sm);
            ChangeListener changeListener = new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    myPanel.setScale((int)spinner.getValue());
                    frame.repaint(10);
                }
            };
            spinner.addChangeListener(changeListener);
            myPanel.setLayout(new BorderLayout());
            myPanel.add(spinner, BorderLayout.NORTH);
            frame.add(myPanel);
            frame.setVisible(true);
            ArrayList<Facility> allObjects = new ArrayList<>();
            allObjects.addAll(portalCranes);
            allObjects.addAll(facilities);
            Thread[] threads = new Thread[5];
                for (int i = 0; i < portalCranes.size(); i++) {
                    threads[i] = new Thread(new MyRunnable(portalCranes.get(i), allObjects, frame));
                    threads[i].start();
                }
    }

    public static double[] convertStringArrayToDoubleArray(String[] s) {
        double[] d = new double[s.length];
        for (int i = 0; i < s.length; i++) {
            d[i] = Double.parseDouble(s[i]);
        }
        return d;
    }

}


