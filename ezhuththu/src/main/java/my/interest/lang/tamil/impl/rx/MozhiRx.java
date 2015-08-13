package my.interest.lang.tamil.impl.rx;

import my.interest.lang.tamil.internal.api.UnicodePatternGenerator;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class MozhiRx implements UnicodePatternGenerator {
    public String generate() {
        return  "(${oarezhuththumozhi})|(${mozhimuthal}${mozhiyidai}*${mozhikkadai}+)";
    }
}