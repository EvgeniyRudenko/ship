package gui;

import geometry.Point;
import objects.Facility;
import objects.PortalCrane;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MyPanel extends JPanel {

    private static int scale = 10;

    public void setScale(int scale) {
        MyPanel.scale = scale;
    }

    private static Point x0y;
    static {
        x0y = new Point(20*scale, 50*scale);
    }

    private ArrayList<PortalCrane> portalCranes;
    private ArrayList<Facility> facilities;

    public MyPanel(ArrayList<PortalCrane> portalCranes, ArrayList<Facility> facilities) {
        //setBorder(BorderFactory.createLineBorder(Color.RED, 5));
        this.portalCranes = portalCranes;
        this.facilities = facilities;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g.setColor(Color.GRAY);
        drawAxis(g);
        g.setColor(Color.RED);
        drawCranes(g);
        g.setColor(Color.BLACK);
        drawShip(g);
        g.setColor(new Color(4, 212, 74));
        drawShipCranesAndStorages(g);
    }

    private void drawLine(Graphics g, Point start, Point end) {
        int startX = (int) (x0y.getX() + start.getX() * scale);
        int startY = (int) (x0y.getY() - start.getY() * scale);
        int endX = (int) (x0y.getX() + end.getX() * scale);
        int endY = (int) (x0y.getY() - end.getY() * scale);
        g.drawLine(startX, startY, endX, endY);
    }

    private void drawShip(Graphics g) {
        double width = 20*scale;
        double height = 20*scale;
        double length = 135*scale;
        double tail = 10*scale;
        Point startPoint = new Point(x0y.getX() + 145*scale, x0y.getY() - 15*scale);
        Point point1 = new Point(startPoint.getX() - width, startPoint.getY() - height / 2);
        Point point2 = new Point(startPoint.getX() - width, startPoint.getY() + height / 2);
        g.drawLine((int)startPoint.getX(), (int)startPoint.getY(), (int)(point1.getX()), (int)(point1.getY()));
        g.drawLine((int)startPoint.getX(), (int)startPoint.getY(), (int)(point2.getX()), (int)(point2.getY()));
        Point point3 = new Point(point1.getX()-length, point1.getY());
        Point point4 = new Point(point2.getX()-length, point2.getY());
        g.drawLine((int) point1.getX(), (int) point1.getY(), (int) point3.getX(), (int) point3.getY());
        g.drawLine((int) point2.getX(), (int) point2.getY(), (int) point4.getX(), (int) point4.getY());
        g.drawArc((int) (point3.getX()-tail/2), (int) point3.getY(), (int) tail, (int) width/2, 90, 90);
        g.drawArc((int) (point4.getX()-tail/2), (int) (point4.getY()-width/2), (int) tail, (int) width/2, -90, -90);
        Point point5 = new Point(point3.getX()-tail/2, point3.getY()+width/4);
        Point point6 = new Point(point4.getX()-tail/2, point4.getY()-width/4);
        g.drawLine((int) point5.getX(), (int) point5.getY(), (int) point6.getX(), (int) point6.getY());
    }

    private void drawAxis(Graphics g) {
        g.drawLine((int) (x0y.getX()), (int) x0y.getY(), (int) (x0y.getX() + 170*scale), (int) x0y.getY());
        g.drawLine((int) (x0y.getX()), (int) x0y.getY() - 45*scale, (int) (x0y.getX()), (int) x0y.getY() + 45*scale);
    }

    private void drawCranes(Graphics g) {
        for (int i = 0; i < portalCranes.size(); i++) {
            PortalCrane crane = portalCranes.get(i);
            //drawLine(g, crane.getPoint(0), crane.getPoint(1));
            drawLine(g, crane.getPoint(1), crane.getPoint(2));
            drawLine(g, crane.getPoint(2), crane.getPoint(3));
            drawLine(g, crane.getPoint(3), crane.getPoint(4));
            drawLine(g, crane.getPoint(4), crane.getPoint(5));
            drawLine(g, crane.getPoint(5), crane.getPoint(1));
            drawLine(g, crane.getPoint(5), crane.getPoint(2));
        }
    }

    private void drawShipCranesAndStorages(Graphics g) {
        for (int i = 0; i < facilities.size(); i++) {
            Facility facility = facilities.get(i);
            if (i==facilities.size()-1)
                g.setColor(new Color(12, 86, 212));
            drawLine(g, facility.getPoint(0), facility.getPoint(1));
            drawLine(g, facility.getPoint(1), facility.getPoint(2));
            drawLine(g, facility.getPoint(2), facility.getPoint(3));
            drawLine(g, facility.getPoint(3), facility.getPoint(0));
        }
    }
}

