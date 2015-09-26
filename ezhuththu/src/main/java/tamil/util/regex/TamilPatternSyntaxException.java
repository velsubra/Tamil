package tamil.util.regex;

import java.util.regex.PatternSyntaxException;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TamilPatternSyntaxException extends PatternSyntaxException {

    public TamilPatternSyntaxException(String desc, String regex, int index) {
        super(desc, regex, index);
    }
}
