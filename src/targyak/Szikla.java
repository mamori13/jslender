package targyak;

import javax.swing.ImageIcon;

/**
 * Segít, hogy a sziklákat megkülönböztessük a többi tárgytól.
 */
public class Szikla extends Targy {

    public Szikla(int x, int y) {
        super(x, y);
        super.kep = new ImageIcon("images/szikla.png").getImage();
    }
}
