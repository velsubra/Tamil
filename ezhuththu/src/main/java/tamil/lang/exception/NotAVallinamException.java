package tamil.lang.exception;

import tamil.lang.TamilCharacter;

/**
 * <p>
 *     Throws when the character is not vallinam.
 * </p>
 * @see  tamil.lang.TamilCharacter#getInaMellinam()
 * @author velsubra
 */
public class NotAVallinamException extends TamilPlatformException {
    public NotAVallinamException(TamilCharacter ch) {
        super("Not a vallinam:" + ch);
    }
}
