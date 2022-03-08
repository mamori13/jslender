package targyak;

import javax.swing.ImageIcon;

/**
 * Segít, hogy a nagyfákat megkülönböztessük a többi tárgytól.
 */
public class NagyFa extends Targy {

    public NagyFa(int x, int y) {
        super(x, y);
        super.kep = new ImageIcon("images/nagyfa.png").getImage();
    }

}
