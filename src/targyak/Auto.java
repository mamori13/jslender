package targyak;

import javax.swing.ImageIcon;

/**
 * Segít, hogy az autókat megkülönböztessük a többi tárgytól.
 */
public class Auto extends Targy {

    public Auto(int x, int y) {
        super(x, y);
        super.kep = new ImageIcon("images/auto.png").getImage();
    }
}
