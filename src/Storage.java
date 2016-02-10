import geometry.Point;

public class Storage extends Facility{

    private int count;
    private static int totalnumber = 0;

    @Override
    public int getCount() {
        return count;
    }

    public Storage(Point... points) {
        super(points);
        totalnumber++;
        count=totalnumber;
    }

}