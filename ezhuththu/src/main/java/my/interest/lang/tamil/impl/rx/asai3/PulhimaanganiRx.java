package my.interest.lang.tamil.impl.rx.asai3;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class PulhimaanganiRx extends YaappuBaseRx {

    public PulhimaanganiRx() {
        super("புளிமாங்கனி");
    }
    public String generate(FeatureSet featureSet) {
        return  "(?:${ntirai}${ntear}${ntirai})";
        //return  "(?:(?!(?:${karuvilham}))(?:${ntirai}${ntear}${ntirai}))";
        //return  "(?:(?!(?:${karuvilham}))(?:${ntirai}${ntear}${ntirai})|(?:${ntirai}${kurril}${mey}+${ntirai}))";
    }
}