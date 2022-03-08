package Game;

import Game.GUI.JatekPanel;

import java.util.Objects;

/**
 * Pozíciók, azaz x és y koordináta-pontok, tárolását végzi.
 */
public class Pozicio {
    public int x;
    public int y;
    public int targySzam;

    public Pozicio(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Konstruktor
     * @param targySzam Minden tárgynak más a száma. Ez alapján különböztetjük meg őket.
     * @param x X koordináta, azaz az oszlop száma 0-14 között.
     * @param y Y koordináta, azaz a sor száma 0-14 között.
     */
    public Pozicio(int targySzam, int x, int y) {
        this.targySzam = targySzam;
        setX(x);
        setY(y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x * JatekPanel.MEZO_MERET;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y * JatekPanel.MEZO_MERET;
    }

    /**
     * A List contains() metódus pontos működése érdekében felül kellett írni az equals() metódust.
     * @param obj Ehhez az elemhez hasonlítunk egy másikat.
     * @return Igaz, ha egyenlőek, hamis, ha különbözőek. (A hashcode alapján dönti el.)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Pozicio)) return false;
        if (getClass() != obj.getClass()) return false;
        if (obj == this) return true;

        Pozicio pozicio = (Pozicio) obj;
        return x == pozicio.x && y == pozicio.y;
    }

    /**
     * A felülírt hashCode() metódus biztosítja, hogy az ugyanolyan listaelemek ugyanazt a hashcode-ot kapják.
     * @return Az objektum hashcode-ját.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
