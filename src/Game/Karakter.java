package Game;

import java.awt.Image;
import java.util.Random;

/**
 * Az "emberi" karakterek közös tulajdonságait tartalmazza.
 */
public class Karakter {
    protected Image kep;
    protected int x;
    protected int y;
    Random random = new Random(System.currentTimeMillis());

    public Image getKep() {
        return kep;
    }

    public void setKep(Image kep) {
        this.kep = kep;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
