package my.interest.lang.tamil.impl.rx.asai3;

import my.interest.lang.tamil.impl.FeatureSet;
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
    public String generate(FeatureSet featureSet) {
       // return  "(?:(?!(?:${ntirai})|${koovilham})(?:${ntear}{3})|(?:${kurril}${mey}+${ntear}{2})|(?:${ntedil}${mey}+${ntear}{2}))";
        return  "(?:(?!(?:(?:${ntirai})|${koovilham}).*)(?:${ntear}{3}))";
    }
}