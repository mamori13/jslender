package targyak;

import javax.swing.ImageIcon;

/**
 * Segít, hogy a teherautókat megkülönböztessük a többi tárgytól.
 */
public class Teherauto extends Targy {

    public Teherauto(int x, int y) {
        super(x, y);
        super.kep = new ImageIcon("images/teherauto.png").getImage();
    }
}
