package tamil.lang.exception;

/**
 * <p>
 *      Raised when {@link tamil.lang.TamilCharacter#getUyirPart()}  is on a character (எ.கா) மெய்யெழுத்து) ) that does not contain it
 * </p>
 *
 * @author velsubra
 */
public class NoUyirPartException extends TamilPlatformException {

    public  NoUyirPartException(String msg) {
        super(msg);
    }
}
