package tamil.lang.exception;

/**
 * <p>
 *     Raised when {@link tamil.lang.TamilCharacter#getMeiPart()} is on a character (எ.கா) உயிரெழுத்து) ) that does not contain it
 * </p>
 *
 * @author velsubra
 */
public class NoMeiPartException extends TamilPlatformException {
    public  NoMeiPartException(String msg) {
        super(msg);
    }
}
