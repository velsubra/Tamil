package my.interest.lang.tamil.impl.rx.asai3;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TheamaanganiRx extends YaappuBaseRx {

    public TheamaanganiRx() {
        super("தேமாங்கனி");
    }
    public String generate(FeatureSet featureSet) {

        return  "(?:(?!(?:${ntirai})|${koovilham})(?:${ntear}{2}${ntirai}))";
        //return  "(?:(?!(?:${ntirai})|${koovilham})(?:${ntear}{2}${ntirai})|(?:${kurril}${mey}+${ntear}${ntirai}))";
    }
}