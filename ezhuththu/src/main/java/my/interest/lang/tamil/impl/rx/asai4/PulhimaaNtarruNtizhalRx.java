package my.interest.lang.tamil.impl.rx.asai4;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class PulhimaaNtarruNtizhalRx extends YaappuBaseRx {

    public PulhimaaNtarruNtizhalRx() {
        super("புளிமாநறுநிழல்");
    }
    public String generate(FeatureSet featureSet) {
        return  "(?:${ntirai}${ntear}(?:${ntirai}){2})";

    }
}