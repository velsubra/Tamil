package my.interest.lang.tamil.impl.rx.asai1;

import my.interest.lang.tamil.impl.yaappu.AsaiRx;
import my.interest.lang.tamil.internal.api.PatternGenerator;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final class NtiraiRx  extends AsaiRx {

    public NtiraiRx() {
        super("நிரை");
    }

    public String generate() {
        return  "((${kurril}${ntedil}${ottu})|(${kurril}${ntedil})|(${kurril}${kurril}${ottu})|(${kurril}${kurril}))";
    }
}