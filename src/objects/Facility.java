package objects;

import geometry.Point;

import java.util.ArrayList;
import java.util.Collections;

public class Facility {

    protected ArrayList<Point> points = new ArrayList<>();
    private int count;
    private static int totalnumber = 0;

    public int getCount() {
        return count;
    }

    public Facility(Point ... points) {
        Collections.addAll(this.points, points);
        totalnumber++;
        count=totalnumber;
    }

    public Point getPoint(int n) {
        n--;
        if (n < 0 || n > points.size()-1)
            throw new IllegalArgumentException("Неверная точка: " + (n+1));
        return points.get(n);
    }

    @Override
    public String toString() {
        String output = getClass().getSimpleName() + " " + getCount();
        for (int i = 0; i < points.size(); i++) {
             output += System.lineSeparator() + points.get(i).toString().replace(":", " " + i + ":");
        }
        return output;
    }
}

