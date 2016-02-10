import geometry.Point;

import java.util.Collections;

import static java.lang.Math.*;

public class PortalCrane extends Facility {

    double width = 6;
    double height = 6;
    private int count;
    private static int totalnumber = 0;

    public int getCount() {
        return count;
    }

    public PortalCrane(Point... points) {
        super(points);
        Collections.addAll(super.points, getPoints2To5());
        totalnumber++;
        count = totalnumber;
    }

    public String crash(Facility facility) {
        //int isPortalCrane = (facility instanceof PortalCrane) ? 1 : 0;
        String output = "";
        double value;
        for (int i = 1; i < points.size(); i++) {
            for (int j = 1; j < facility.points.size(); j++) {
                if (j != facility.points.size() - 1)
                    value = points.get(i).crash(facility.getPoint(j), facility.getPoint(j + 1));
                else
                    value = points.get(i).crash(facility.getPoint(j), facility.getPoint(1));
                output += String.format("%d %d %d %d %3.2f", count, i, facility.getCount(), j, value)+System.lineSeparator();
            }
            output+=System.lineSeparator();
        }
        return output;
    }


    @Override
    public Point getPoint(int n) {
        return super.getPoint(++n);
    }

    private Point[] getPoints2To5() {
        double angle01y = atan((getPoint(1).getX() - getPoint(0).getX()) /
                (getPoint(1).getY() - getPoint(0).getY()));
        if (getPoint(1).getY() < 0) angle01y += Math.PI;
        Point[] points2To5 = new Point[4];
        for (int i = 0; i < 4; i++) {
            double radiusVector0n = getradiusVector0n(i + 2);
            double angel01n = getAngle01y(i + 2);
            double x = getPoint(0).getX() + radiusVector0n * sin(angle01y + angel01n);
            double y = getPoint(0).getY() + radiusVector0n * cos(angle01y + angel01n);
            points2To5[i] = new Point(x, y);
        }
        return points2To5;
    }

    private double getAngle01y(int n) {
        double angel01n = 0;
        switch (n) {
            case 2:
                angel01n = Math.PI / 2;
                break;
            case 3:
                angel01n = Math.PI / 2 + atan(height / (width / 2));
                break;
            case 4:
                angel01n = 3 * Math.PI / 2 - atan(height / (width / 2));
                break;
            case 5:
                angel01n = 3 * Math.PI / 2;
                break;
        }
        return angel01n;
    }

    private double getradiusVector0n(int n) {
        double radiusVector0n = 0;
        switch (n) {
            case 2:
                radiusVector0n = width / 2;
                break;
            case 3:
                radiusVector0n = sqrt(pow(width / 2, 2) + pow(height, 2));
                break;
            case 4:
                radiusVector0n = sqrt(pow(width / 2, 2) + pow(height, 2));
                break;
            case 5:
                radiusVector0n = width / 2;
                break;
        }
        return radiusVector0n;
    }

}
