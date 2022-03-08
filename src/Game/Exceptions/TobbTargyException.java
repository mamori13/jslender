package Game.Exceptions;

/**
 * Új kivétel objektumot hoz létre. Abban segít, hogy saját pálya generálásakor ne tudjon a felhasználó
 * több tárgyat generálni ugyanarra a mezőre.
 */
public class TobbTargyException extends Exception{
    public TobbTargyException() {
    }
}
