package objects;

import geometry.Point;
import objects.Facility;

public class Storage extends Facility {

    private int count;
    private static int totalnumber = 0;

    @Override
    public int getCount() {
        //return count;
        return super.getCount();
    }

    public Storage(Point... points) {
        super(points);
        totalnumber++;
        count=totalnumber;
    }

}
