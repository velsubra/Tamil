package my.interest.lang.tamil.impl.rx.asai2;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final class KoovilhamRx  extends YaappuBaseRx {

    public KoovilhamRx() {
        super("கூவிளம்");
    }
    public String generate(FeatureSet featureSet) {
        return  "(?:(?!(?:${ntirai}))(?:${ntear}${ntirai}))";
        //return  "(?:(?!(?:${ntirai}))(?:${ntear}${ntirai})|(?:${kurril}${mey}+${ntirai})|(?:${ntedil}${mey}+${ntirai}))";
    }
}