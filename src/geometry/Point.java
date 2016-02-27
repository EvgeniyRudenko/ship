package geometry;

import static java.lang.Math.*;

public class Point {

    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDistanceTo(Point point) {
        return sqrt(pow(x - point.x, 2) + pow(y - point.y, 2));
    }

    public double getDistanceTo(Line line) {
        return Math.abs(line.getA() * x + line.getB() * y + line.getC()) / sqrt(pow(line.getA(), 2) + pow(line.getB(), 2));
    }

    public Point getPerpendicularBasePoint(Point point1, Point point2) {
        Line line = new Line(point1, point2);
        return line.getPerpendicularLine(this).getIntersectionPoint(line);
    }

    public boolean belongsToLine(Point point1, Point point2) {
        double minX = min(point1.getX(), point2.getX());
        double maxX = max(point1.getX(), point2.getX());
        double minY = min(point1.getY(), point2.getY());
        double maxY = max(point1.getY(), point2.getY());
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }

    public double crash(Point point1, Point point2) {
        Line line = new Line(point1, point2);
        Point point = getPerpendicularBasePoint(point1, point2);
        if (point.belongsToLine(point1, point2))
            return getDistanceTo(line);
        return 100;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + String.format(": X = %6.2f Y = %6.2f", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

}


