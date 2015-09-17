package my.interest.lang.tamil.impl.rx.asai3;

import my.interest.lang.tamil.impl.yaappu.AsaiRx;
import my.interest.lang.tamil.internal.api.PatternGenerator;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TheamaangaayRx extends AsaiRx {

    public TheamaangaayRx() {
        super("தேமாங்காய்");
    }
    public String generate() {
        return  "((?!(${ntirai})|${koovilham})(${ntear}${ntear}${ntear})|(${kurril}${mey}+${ntear}${ntear})|(${ntedil}${mey}+${ntear}${ntear}))";
        //return  "((?!((${ntirai})|${koovilham}).*)(${ntear}${ntear}${ntear}))";
    }
}