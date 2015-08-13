package my.interest.lang.tamil.impl.rx.asai1;

import my.interest.lang.tamil.internal.api.UnicodePatternGenerator;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class NtearRx implements UnicodePatternGenerator {
    public String generate() {
        return  "((${ntedil}${ottu})|(${kurril}${ottu})|(${ntedil})|(${kurril}))";
    }
}