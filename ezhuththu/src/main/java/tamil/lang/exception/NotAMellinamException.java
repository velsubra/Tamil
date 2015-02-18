package tamil.lang.exception;

import tamil.lang.TamilCharacter;

/**
 * <p>
 *      Throws when the character is not mellinam.
 * </p>
 *
 * @see tamil.lang.TamilCharacter#getInaVallinam()
 * @author velsubra
 */
public class NotAMellinamException extends TamilPlatformException {
    public NotAMellinamException(TamilCharacter ch) {
        super("Not a mellinam:" + ch);
    }
}
