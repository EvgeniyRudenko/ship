package objects;

import geometry.Point;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static java.lang.Math.*;
import static java.lang.Thread.sleep;

public class PortalCrane extends Facility implements Cloneable{

    double width = 6;
    double height = 6;
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

    public void rotate(double angle, ArrayList<Facility> facilities, JFrame jFrame){
        double step = 0.5*Math.signum(angle);
        int numberOfSteps = (int) (abs(angle/step));
        for (int i = 0; i < numberOfSteps; i++) {
            if (!rotate1Step(step, facilities, jFrame)){
                isStopped=true;
                return;
            }
        }
    }

    private boolean rotate1Step(double angle, ArrayList<Facility> facilities, JFrame jFrame) {
        if (!canMove(angle, facilities, 2)) return false;
        recalculatePointsForRotation(angle);
        jFrame.repaint();
        try {
            sleep(50);
        } catch (InterruptedException e) {
        }
        return true;
    }

    public void move(double distance, ArrayList<Facility> facilities, JFrame jFrame){
        double step = 0.5*Math.signum(distance);
        int numberOfSteps = (int) (abs(distance/step));
        for (int i = 0; i < numberOfSteps; i++) {
            if (!move1Step(step, facilities, jFrame)){
                isStopped=true;
                return;
            }
        }
    }

    public void doRandomMove(ArrayList<Facility> facilities, JFrame jFrame){
        int sign = new Random().nextInt(10) % 2 == 0 ? 1 : -1;
        if (new Random().nextInt(10) % 2 == 0)
            rotate(new Random().nextInt(180) * sign, facilities, jFrame);
        else
            move(new Random().nextInt(10) * sign, facilities, jFrame);
    }

    private boolean move1Step(double step, ArrayList<Facility> facilities, JFrame jFrame){
        if (!canMove(step, facilities, 1)) return false;
        recalculatePointsForMoving(step);
        jFrame.repaint();
        try {
            sleep(50);
        } catch (InterruptedException e) {
        }
        return true;
    }

    private void recalculatePointsForMoving(double distance){
        for (Point point : points) {
            point.setX(point.getX() + distance);
        }
    }

    private void recalculatePointsForRotation(double angle){
        Point center = new Point(
                (getPoint(2).getX() + getPoint(4).getX()) / 2,
                (getPoint(2).getY() + getPoint(4).getY()) / 2
        );
        //Point center = new Point(getPoint(0).getX(), getPoint(0).getY()); // вращение вокруг точки 0
        for (int i = 0; i < points.size(); i++) {
            double x = getPoint(i).getX()-center.getX();
            double y = getPoint(i).getY()-center.getY();
            double newPointX = center.getX() + x * cos(angle * Math.PI / 180) + y * sin(angle * Math.PI / 180);
            double newPointY = center.getY() + -x * sin(angle * Math.PI / 180) + y * cos(angle * Math.PI / 180);
            getPoint(i).setX(newPointX);
            getPoint(i).setY(newPointY);
        }
    }

    private boolean canMove(double step, ArrayList<Facility> facilities, int n){
        for (int i = 0; i < facilities.size(); i++) {
            if (this.equals(facilities.get(i))) continue;
            ArrayList<Double> list = getFullListOfDistances(facilities.get(i));
            Collections.sort(list);
            double min = list.get(0);
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j) < 1){
                    if (n==1)
                        recalculatePointsForMoving(step);
                    else
                        recalculatePointsForRotation(step);
                    ArrayList<Double> list2 = getFullListOfDistances(facilities.get(i));
                    Collections.sort(list2);
                    double min2 = list2.get(0);
                    if (n==1)
                        recalculatePointsForMoving(-step);
                    else
                        recalculatePointsForRotation(-step);
                    if (min2>min) {
                        isStopped = true;
                    }
                    else {
                        isStopped = false;
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private Point[] getPoints2To5() {
        double angle01y;
        if (abs(getPoint(0).getY()-getPoint(1).getY())<0.001){
            angle01y = Math.PI/2;
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
        return count == crane.count;

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
