package my.interest.lang.tamil.impl.rx.asai2;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final class PulhimaRx  extends YaappuBaseRx {

    public PulhimaRx() {
        super("புளிமா");
    }
    public String generate(FeatureSet featureSet) {
        return  "(?:${ntirai}${ntear})";
    }
}