package my.interest.lang.tamil.impl.rx.asai3;

import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TheamaangaayRx extends YaappuBaseRx {

    public TheamaangaayRx() {
        super("தேமாங்காய்");
    }
    public String generate() {
        return  "((?!(${ntirai})|${koovilham})(${ntear}${ntear}${ntear})|(${kurril}${mey}+${ntear}${ntear})|(${ntedil}${mey}+${ntear}${ntear}))";
       // return  "((?!((${ntirai})|${koovilham}).*)(${ntear}${ntear}${ntear}))";
    }
}