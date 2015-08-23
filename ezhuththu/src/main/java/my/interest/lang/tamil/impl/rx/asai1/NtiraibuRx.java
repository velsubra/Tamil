package my.interest.lang.tamil.impl.rx.asai1;

import my.interest.lang.tamil.impl.yaappu.AsaiRx;
import my.interest.lang.tamil.internal.api.PatternGenerator;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final  class NtiraibuRx extends AsaiRx {

    public NtiraibuRx() {
        super("நிரைபு");
    }

    public String generate() {
        return  "(${ntirai}${valiyugaravarisai})";
    }
}
