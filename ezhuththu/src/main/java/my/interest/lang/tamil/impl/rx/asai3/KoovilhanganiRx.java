package my.interest.lang.tamil.impl.rx.asai3;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class KoovilhanganiRx  extends YaappuBaseRx {

    public KoovilhanganiRx() {
        super("கூவிளங்கனி");
    }
    public String generate(FeatureSet featureSet) {

        //return  "(?:(?!(?:${ntirai}))(?:${ntear}${ntirai}{2})|(?:${kurril}${mey}+${ntirai}{2})|(?:${ntedil}${mey}+${ntirai}{2}))";
        return  "(?:(?!(?:${ntirai}))(?:${ntear}${ntirai}{2}))";
    }
}