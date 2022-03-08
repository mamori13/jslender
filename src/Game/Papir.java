package Game;

import javax.swing.ImageIcon;
import java.awt.Image;

/**
 * A keresendő papírok tulajdonságait írja le.
 */
public class Papir {
    private int x ;
    private int y;
    Image kep;

    public Papir(int x, int y) {
        this.x = x;
        this.y = y;
        kep = new ImageIcon("images/papir.png").getImage();
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

    public Image getKep() {
        return kep;
    }
}
