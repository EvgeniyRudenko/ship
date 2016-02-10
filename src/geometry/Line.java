package geometry;

// (y1-y2)*x + (x2-x1)*y+(x1*y2-x2*y1)=0
// ax + by + c = 0;
public class Line{

    private Point point1;
    private Point point2;

    private double a;
    private double b;
    private double c;

    public Line(Point point1, Point point2) {
        if (point1.equals(point2))
            throw new IllegalArgumentException("Не могу построить прямую, используя 2 одинаковые точки.");
        this.point1 = point1;
        this.point2 = point2;
        a = point1.getY()-point2.getY();
        b = point2.getX()-point1.getX();
        c = point1.getX()*point2.getY()-point2.getX()*point1.getY();
    }

    public Line(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Point getPoint1() {
        return point1;
    }

    public void setPoint1(Point point1) {
        this.point1 = point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public void setPoint2(Point point2) {
        this.point2 = point2;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public Line getPerpendicularLine(Point point){
        double a = this.b;
        double b = -this.a;
        double c =this.a*point.getY()-this.b*point.getX();
        return new Line(a, b, c);
    }

    public Point getIntersectionPoint(Line line){
        double x = -(c*line.getB()-line.getC()*b)/(a*line.getB()-line.getA()*b);
        double y = -(a*line.getC()-line.getA()*c)/(a*line.getB()-line.getA()*b);
        return new Point(x,y);
    }

    @Override
    public String toString() {
        return getClass().getName() + ": A = " + getA() + ", B = " + getB() + ", C = " + getC();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Line line = (Line) o;

        return Double.compare(line.a, a) == 0 && Double.compare(line.b, b) == 0 && Double.compare(line.c, c) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(a);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(b);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(c);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
