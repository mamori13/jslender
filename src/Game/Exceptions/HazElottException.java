package Game.Exceptions;

/**
 * Új kivétel objektumot hoz létre. Azt is vizsgáljuk, hogy a ház egyik bejárata előtt se legyen
 * akadály objektum, ugyanis ilyenkor nem lehet hozzáférni az ajtóhoz.
 */
public class HazElottException extends Exception {
    public HazElottException() {
    }
}
