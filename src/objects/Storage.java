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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Storage storage = (Storage) o;

        if (count != storage.count) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + count;
        return result;
    }
}
