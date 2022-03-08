package targyak;

import javax.swing.ImageIcon;

/**
 * Segít, hogy a házat megkülönböztessük a többi tárgytól.
 */
public class Haz extends Targy {

    public Haz (int x, int y) {
        super(x, y);
        super.kep = new ImageIcon("images/haz.png").getImage();
    }
}
