package my.interest.lang.tamil.impl.rx;

import my.interest.lang.tamil.internal.api.UnicodePatternGenerator;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class NonTamilSymbolRx implements UnicodePatternGenerator {
    public String generate() {
        return  "[\\u0000-\\u0B01\\u0BCC-\\uFFFF]";
    }
}
