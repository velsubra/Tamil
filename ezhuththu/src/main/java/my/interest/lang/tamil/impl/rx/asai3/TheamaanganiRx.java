package my.interest.lang.tamil.impl.rx.asai3;

import my.interest.lang.tamil.impl.yaappu.AsaiRx;
import my.interest.lang.tamil.internal.api.PatternGenerator;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TheamaanganiRx extends AsaiRx {

    public TheamaanganiRx() {
        super("தேமாங்கனி");
    }
    public String generate() {

        return  "((?!(${ntirai})|${koovilham})(${ntear}${ntear}${ntirai})|(${kurril}${mey}+${ntear}${ntirai}))";
    }
}