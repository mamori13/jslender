package targyak;

import javax.swing.ImageIcon;

/**
 * Segít, hogy a kisfákat megkülönböztessük a többi tárgytól.
 */
public class KisFa extends Targy {

    public KisFa(int x, int y) {
        super(x, y);
        super.kep = new ImageIcon("images/kisfa.png").getImage();
    }


}
