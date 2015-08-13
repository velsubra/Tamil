package my.interest.lang.tamil.impl.rx;

import my.interest.lang.tamil.internal.api.UnicodePatternGenerator;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TamilSymbolRx implements UnicodePatternGenerator {
    public String generate() {
       return  "[\\u0B02-\\u0BCD]";
    }
}
