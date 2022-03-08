package targyak;

import java.awt.Image;

/**
 * A az összes pályán lévő tárgy őse.
 */
public class Targy {
    protected int x;
    protected int y;
    protected Image kep;

    public Targy(int x, int y) {
        setX(x);
        setY(y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x*46;
    }

    public int getY() {
        return y;
    }

    public void setY(int y){
        this.y = y*46;
    }

    public Image getKep() {
        return kep;
    }

}
