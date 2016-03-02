package objects;

import geometry.Point;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static java.lang.Math.*;
import static java.lang.Thread.sleep;

public class PortalCrane extends Facility implements Cloneable {

    private double width = 6;
    private double height = 6;
    private int count;
    private static int totalnumber = 0;
    private boolean isStopped = false;

    public int getCount() {
        //return count;
        return super.getCount();
    }

    @Override
    public int getStartPoint() {
        return 1;
    }

    public PortalCrane(Point... points) {
        super(points);
        Collections.addAll(super.points, getPoints2To5());
        totalnumber++;
        count = totalnumber;
    }

    public void rotate(double angle, ArrayList<Facility> facilities, JFrame jFrame) {
        double step = 0.5 * Math.signum(angle);
        int numberOfSteps = (int) (abs(angle / step));
        for (int i = 0; i < numberOfSteps; i++) {
            rotate1Step(step, facilities, jFrame);
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isStopped)
                return;
        }
    }

    private void rotate1Step(double angle, ArrayList<Facility> facilities, JFrame jFrame) {
        if (!canMove(angle, facilities, 2)) {
            isStopped = true;
            return;
        }
        isStopped = false;
        recalculatePointsForRotation(angle);
        jFrame.repaint();
    }

    public void move(double distance, ArrayList<Facility> facilities, JFrame jFrame) {
        double step = 0.5 * Math.signum(distance);
        int numberOfSteps = (int) (abs(distance / step));
        for (int i = 0; i < numberOfSteps; i++) {
            move1Step(step, facilities, jFrame);
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isStopped)
                return;
        }
    }

    public void doRandomMove(ArrayList<Facility> facilities, JFrame jFrame) {
        int sign = new Random().nextInt(10) % 2 == 0 ? 1 : -1;
        switch (new Random().nextInt() % 3) {
            case 0:
                rotate(new Random().nextInt(180) * sign, facilities, jFrame);
                break;
            case 1:
                move(new Random().nextInt(10) * sign, facilities, jFrame);
                break;
            case 2:
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                }
                break;
        }
    }

    private void move1Step(double step, ArrayList<Facility> facilities, JFrame jFrame) {
        if (!canMove(step, facilities, 1)) {
            isStopped = true;
            return;
        }
        isStopped = false;
        recalculatePointsForMoving(step);
        jFrame.repaint();
    }

    private void recalculatePointsForMoving(double distance) {
        for (Point point : points) {
            point.setX(point.getX() + distance);
        }
    }

    private void recalculatePointsForRotation(double angle) {
        Point center = new Point(
                (getPoint(2).getX() + getPoint(4).getX()) / 2,
                (getPoint(2).getY() + getPoint(4).getY()) / 2
        );
        //Point center = new Point(getPoint(0).getX(), getPoint(0).getY()); // вращение вокруг точки 0
        for (int i = 0; i < points.size(); i++) {
            double x = getPoint(i).getX() - center.getX();
            double y = getPoint(i).getY() - center.getY();
            double newPointX = center.getX() + x * cos(angle * Math.PI / 180) + y * sin(angle * Math.PI / 180);
            double newPointY = center.getY() + -x * sin(angle * Math.PI / 180) + y * cos(angle * Math.PI / 180);
            getPoint(i).setX(newPointX);
            getPoint(i).setY(newPointY);
        }
    }

    private boolean canMove(double step, ArrayList<Facility> facilities, int n) {

        double min = getMinDistance(facilities, this, this);
        if (!isStopped)
            return min > 1.5;

        PortalCrane crane = new PortalCrane(new Point(getPoint(0).getX(), getPoint(0).getY()),
                new Point(getPoint(1).getX(), getPoint(1).getY()));
        if (n == 1)
            crane.recalculatePointsForMoving(step);
        else
            crane.recalculatePointsForRotation(step);

        double min2 = crane.getMinDistance(facilities, this, crane);
        return  min2 > 1.5 || min2 > min;

    }

    private double getMinDistance(ArrayList<Facility> facilities, Facility facility1, Facility facility2) {
        double result = 100;
        for (int i = 0; i < facilities.size(); i++) {
            Facility current = facilities.get(i);
            if (this.equals(current) || facility1.equals(current) ||  facility2.equals(current)) continue;
            ArrayList<Double> list = getFullListOfDistances(facilities.get(i));
            Collections.sort(list);
            result = min(result, list.get(0));
        }
        return result;
    }

    private Point[] getPoints2To5() {
        double angle01y;
        if (abs(getPoint(0).getY() - getPoint(1).getY()) < 0.001) {
            angle01y = Math.PI / 2;
        } else {
            angle01y = atan((getPoint(1).getX() - getPoint(0).getX()) /
                    (getPoint(1).getY() - getPoint(0).getY()));
        }
        if (getPoint(1).getY() < 0) angle01y += Math.PI;
        Point[] points2To5 = new Point[4];
        for (int i = 0; i < 4; i++) {
            double radiusVector0n = getRadiusVector0n(i + 2);
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

    private double getRadiusVector0n(int n) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PortalCrane crane = (PortalCrane) o;

        if (Double.compare(crane.width, width) != 0) return false;
        if (Double.compare(crane.height, height) != 0) return false;
        return true;
        //return count == crane.count;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(width);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(height);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + count;
        return result;
    }
}
