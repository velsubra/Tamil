package my.interest.lang.tamil.impl.rx.asai2;

import my.interest.lang.tamil.impl.yaappu.AsaiRx;
import my.interest.lang.tamil.internal.api.PatternGenerator;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final class KoovilhamRx  extends AsaiRx {

    public KoovilhamRx() {
        super("கூவிளம்");
    }
    public String generate() {
        return  "((?!(${ntirai}))(${ntear}${ntirai})|(${kurril}${mey}+${ntirai})|(${ntedil}${mey}+${ntear}))";
    }
}