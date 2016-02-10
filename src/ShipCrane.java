import geometry.Point;

public class ShipCrane extends Facility{

    private int count;
    private static int totalnumber = 0;

    @Override
    public int getCount() {
        return count;
    }

    public ShipCrane(Point... points) {
        super(points);
        totalnumber++;
        count=totalnumber;
    }

}
