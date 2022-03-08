package Game.Exceptions;

/**
 * Új kivétel objektumot hoz létre. Arra használjuk, hogy megvizsgáljuk, valóban csak egy házat szeretne
 * létrehozni a felhasználó saját pálya feltöltésekor. Ha több házat akar feltölteni, hibát dobunk, hisz
 * ezt a feledat leírása tiltja.
 */
public class EgyHazException extends Exception{
    public EgyHazException() {
    }
}
