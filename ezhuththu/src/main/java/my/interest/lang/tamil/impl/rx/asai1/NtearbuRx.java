package my.interest.lang.tamil.impl.rx.asai1;

import my.interest.lang.tamil.impl.yaappu.AsaiRx;
import my.interest.lang.tamil.internal.api.PatternGenerator;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final class NtearbuRx  extends AsaiRx {
    public NtearbuRx() {
        super("நேர்பு");
    }

    public String generate() {
        return  "((${ntear})(${valiyugaravarisai}))";
    }
}