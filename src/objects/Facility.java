package objects;

import geometry.Point;

import java.util.ArrayList;
import java.util.Collections;

public class Facility {

    protected ArrayList<Point> points = new ArrayList<>();
    private int count;
    private static int totalnumber = 0;

    public int getStartPoint(){
        return 0;
    }

    public int getCount() {
        return count;
    }

    public Facility(Point ... points) {
        Collections.addAll(this.points, points);
        totalnumber++;
        count=totalnumber;
    }

    public int getNumberOfPoints(){
        return points.size();
    }

    public Point getPoint(int n) {
        if (n < 0 || n > points.size()-1)
            throw new IllegalArgumentException("Неверная точка: " + n);
        return points.get(n);
    }

    public String crash(Facility facility) {
        String output = "";
        double value;
        for (int i = getStartPoint(); i < points.size(); i++) {
            for (int j = facility.getStartPoint(); j < facility.points.size(); j++) {
                if (j != facility.points.size() - 1)
                    value = points.get(i).crash(facility.getPoint(j), facility.getPoint(j + 1));
                else
                    value = points.get(i).crash(facility.getPoint(j), facility.getPoint(facility.getStartPoint()));
                output += String.format("%d %d %d %d%d %7.2f", getCount(), i, facility.getCount(),
                        j, j != facility.points.size() - 1 ? j + 1 : facility.getStartPoint(), value) + System.lineSeparator();
            }
            output += System.lineSeparator();
        }
        return output;
    }

    public ArrayList<Double> getFullListOfDistances(Facility facility){
        ArrayList<Double> list = new ArrayList<>();
        list.addAll(getDistancesTo(facility));
        list.addAll(facility.getDistancesTo(this));
        return list;
    }

    private ArrayList<Double> getDistancesTo(Facility facility){
        ArrayList<Double> list = new ArrayList<>();
        double value;
        for (int i = getStartPoint(); i < points.size(); i++) {
            for (int j = facility.getStartPoint(); j < facility.points.size(); j++) {
                if (j != facility.points.size() - 1)
                    value = points.get(i).crash(facility.getPoint(j), facility.getPoint(j + 1));
                else
                    value = points.get(i).crash(facility.getPoint(j), facility.getPoint(facility.getStartPoint()));
                list.add(value);
            }
        }
        return list;
    }

    @Override
    public String toString() {
        String output = getClass().getSimpleName() + " " + getCount();
        for (int i = 0; i < points.size(); i++) {
             output += System.lineSeparator() + points.get(i).toString().replace(":", " " + i + ":");
        }
        return output;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Facility facility = (Facility) o;

        if (count != facility.count) return false;
        return !(points != null ? !points.equals(facility.points) : facility.points != null);

    }

    @Override
    public int hashCode() {
        int result = points != null ? points.hashCode() : 0;
        result = 31 * result + count;
        return result;
    }
}

