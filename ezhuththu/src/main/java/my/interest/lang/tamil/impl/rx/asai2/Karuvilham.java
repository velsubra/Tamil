package my.interest.lang.tamil.impl.rx.asai2;

import my.interest.lang.tamil.impl.yaappu.AsaiRx;
import my.interest.lang.tamil.internal.api.PatternGenerator;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final class Karuvilham extends AsaiRx {

    public Karuvilham() {
        super("கருவிளம்");
    }
    public String generate() {
        return  "(${ntirai}${ntirai})";
    }
}