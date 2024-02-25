package carpool.buddy.classes;

import java.util.ArrayList;

public class Sedan extends Vehicle {
    private int range;

    public void setRange(int range) {this.range = range;}

    public int getRange() {return range;}

    public Sedan(String ow, ArrayList<String> ruid, String oe, String m, int c, int tc, String vid, boolean op, String vt, boolean e, int r) {
        super(ow, ruid, oe, m, c, tc, vid, op, vt, e);
        range = r;
    }
}
