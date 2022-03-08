package Game;

import Game.GUI.JatekPanel;

import javax.swing.ImageIcon;
import java.util.Random;

/**
 * A játékos tulajdonságait írja le a Karakter class bővítésével/pontosításával.
 */
public class Jatekos extends Karakter{
    Random random = new Random(System.currentTimeMillis());

    public Jatekos() {
        this.x = kezdoPont();
        this.y = kezdoPont();
        kep = new ImageIcon("images/moss.png").getImage();
    }

    /**
     * A kezdőpont randomizálását végzi.<br>
     * @return 0-át vagy 14-et ad vissza, a random generált szám párosságától függően.
     */
    private int kezdoPont() {
        int seged = random.nextInt(2);
        if (seged == 1) seged = 14;
        return seged * JatekPanel.MEZO_MERET;
    }

    /**
     * Nem engedi, hogy a játékos kisétáljon a pályáról jobb vagy bal oldalról.
     * @param x Az x koordinátát állítja be.
     */
    public void setX(int x) {
        if (x >= 0 && x <= JatekPanel.SZELESSEG - JatekPanel.MEZO_MERET) {
            this.x = x;
        }
    }

    /**
     * Nem engedi, hogy a játékos kisétáljon a pályáról észak vagy dél irányban.
     * @param y Az y koordinátát állítja be.
     */
    public void setY(int y) {
        if (y >= 0 && y <= JatekPanel.MAGASSAG - JatekPanel.MEZO_MERET) {
            this.y = y;
        }
    }
}
