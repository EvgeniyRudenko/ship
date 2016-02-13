package objects;

import geometry.Point;
import objects.Facility;

public class ShipCrane extends Facility {

    private int count;
    private static int totalnumber = 0;

    @Override
    public int getCount() {
        //return count;
        return super.getCount();
    }

    public ShipCrane(Point... points) {
        super(points);
        totalnumber++;
        count=totalnumber;
    }

}
