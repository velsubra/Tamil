package my.interest.lang.tamil.impl.rx.asai3;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class PulhimaangaayRx extends YaappuBaseRx {

    public PulhimaangaayRx() {
        super("புளிமாங்காய்");
    }
    public String generate(FeatureSet featureSet) {
        //return  "(?:(?!(?:${karuvilham}))(?:${ntirai}${ntear}{2})|(?:${ntirai}${kurril}${mey}${ntear}))";
        return  "(?:${ntirai}(?:${ntear}){2})";
        //return  "(?:(?!(?:${karuvilham}))(?:${ntirai}${ntear}{2}))";
    }
}