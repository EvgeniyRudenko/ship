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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ShipCrane shipCrane = (ShipCrane) o;

        return count == shipCrane.count;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + count;
        return result;
    }
}
