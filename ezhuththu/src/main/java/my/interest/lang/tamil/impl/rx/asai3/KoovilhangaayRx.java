package my.interest.lang.tamil.impl.rx.asai3;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class KoovilhangaayRx extends YaappuBaseRx {

    public KoovilhangaayRx() {
        super("கூவிளங்காய்");
    }
    public String generate(FeatureSet featureSet) {

       // return  "(?:(?!(?:${ntirai}))(?:${ntear}${ntirai}${ntear})|(?:${kurril}${mey}${ntirai}${ntear}))";
       // return  "(?:(?!(?:${ntirai}))(?:${ntear}${ntirai}${ntear}))";
        return  "(?:${ntear}${ntirai}${ntear})";
    }
}