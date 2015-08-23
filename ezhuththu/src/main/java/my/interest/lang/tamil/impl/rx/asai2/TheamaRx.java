package my.interest.lang.tamil.impl.rx.asai2;

import my.interest.lang.tamil.impl.yaappu.AsaiRx;
import my.interest.lang.tamil.internal.api.PatternGenerator;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TheamaRx extends AsaiRx {

    public TheamaRx() {
        super("தேமா");
    }
    public String generate() {
        return  "((?!(${ntirai}))(${ntear}${ntear})|(${kurril}${mey}${ntear}))";
    }
}