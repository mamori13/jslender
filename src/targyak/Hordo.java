package targyak;

import javax.swing.ImageIcon;

/**
 * Segít, hogy a hordókat megkülönböztessük a többi tárgytól.
 */
public class Hordo extends Targy {

    public Hordo(int x, int y) {
        super(x, y);
        super.kep = new ImageIcon("images/hordo.png").getImage();
    }
}
