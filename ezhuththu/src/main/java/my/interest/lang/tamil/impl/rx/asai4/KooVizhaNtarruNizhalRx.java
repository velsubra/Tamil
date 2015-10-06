package my.interest.lang.tamil.impl.rx.asai4;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class KooVizhaNtarruNizhalRx extends YaappuBaseRx {

    public KooVizhaNtarruNizhalRx() {
        super("கூவிளநறுநிழல்");
    }
    public String generate(FeatureSet featureSet) {
        return  "(?:${ntear}(?:${ntirai}){3})";

    }
}