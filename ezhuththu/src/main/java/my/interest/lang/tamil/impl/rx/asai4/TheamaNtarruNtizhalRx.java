package my.interest.lang.tamil.impl.rx.asai4;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TheamaNtarruNtizhalRx extends YaappuBaseRx {

    public TheamaNtarruNtizhalRx() {
        super("தேமாநறுநிழல்");
    }
    public String generate(FeatureSet featureSet) {
        return  "(?:(?:${ntear}){2}(?:${ntirai}){2})";

    }
}