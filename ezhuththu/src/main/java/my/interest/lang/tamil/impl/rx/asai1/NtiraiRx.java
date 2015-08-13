package my.interest.lang.tamil.impl.rx.asai1;

import my.interest.lang.tamil.internal.api.UnicodePatternGenerator;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class NtiraiRx  implements UnicodePatternGenerator {
    public String generate() {
        return  "((${kurril}${ntedil}${ottu})|(${kurril}${ntedil})|(${kurril}${kurril}${ottu})|(${kurril}${kurril}))";
    }
}