package my.interest.lang.tamil.impl.rx.asai2;

import my.interest.lang.tamil.internal.api.UnicodePatternGenerator;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class KoovilhamRx  implements UnicodePatternGenerator {
    public String generate() {
        //return  "((?!(${ntirai}))((${ntear})(${ntear})))";
        //  return  "((?!(${ntirai}))(${ntear}${ntear}))";

        // return  "(?>${ntirai}|${ntear}${ntear})";

        return  "(?!(${ntirai}))(${ntear}${ntirai})";
    }
}