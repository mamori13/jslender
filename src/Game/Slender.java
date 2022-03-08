package Game;

import Game.GUI.JatekPanel;

import javax.swing.ImageIcon;

/**
 * Slenderman tulajdonságát írja le a Karakter class bővítésével/pontosításával.
 */
public class Slender extends Karakter{

    public Slender(int x, int y) {
        this.x = x;
        this.y = y;
        kep = new ImageIcon("images/slender.png").getImage();
    }

    /**
     * Slenderman teleportálását végzi. Beállítja Slender tartózkodási helyének x koordinátáját egy random generátor segítségével.
     */
    public void teleportX() {
        setX(random.nextInt(JatekPanel.SZELESSEG / JatekPanel.MEZO_MERET) * JatekPanel.MEZO_MERET);
    }

    /**
     * Slenderman teleportálását végzi. Beállítja Slender tartózkodási helyének y koordinátáját egy random generátor segítségével.
     */
    public void teleportY() {
        setY(random.nextInt(JatekPanel.MAGASSAG / JatekPanel.MEZO_MERET) * JatekPanel.MEZO_MERET);
    }
}
