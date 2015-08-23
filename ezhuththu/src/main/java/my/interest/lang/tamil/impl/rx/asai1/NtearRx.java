package my.interest.lang.tamil.impl.rx.asai1;

import my.interest.lang.tamil.impl.yaappu.AsaiRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final class NtearRx extends AsaiRx {

    public NtearRx() {
        super("நேர்");
    }

    public String generate() {
        return "((${ntedil}${mey})|(${kurril}${mey})|(${ntedil})|(${kurril}))";
    }
}