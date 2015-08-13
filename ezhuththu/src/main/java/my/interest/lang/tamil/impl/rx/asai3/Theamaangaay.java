package my.interest.lang.tamil.impl.rx.asai3;

import my.interest.lang.tamil.internal.api.UnicodePatternGenerator;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class Theamaangaay  implements UnicodePatternGenerator {
    public String generate() {

        return  "(?!(${ntirai})|${koovilham})(${ntear}${ntear}${ntear})";
    }
}