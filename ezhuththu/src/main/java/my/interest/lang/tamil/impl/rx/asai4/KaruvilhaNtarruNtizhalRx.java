package my.interest.lang.tamil.impl.rx.asai4;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class KaruvilhaNtarruNtizhalRx extends YaappuBaseRx {

    public KaruvilhaNtarruNtizhalRx() {
        super("கருவிளநறுநிழல்");
    }
    public String generate(FeatureSet featureSet) {
        return  "(?:(?:${ntirai}){4})";

    }
}