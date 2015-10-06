package my.interest.lang.tamil.impl.rx.asai4;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class KaruvilhaNtarrumBooRx extends YaappuBaseRx {

    public KaruvilhaNtarrumBooRx() {
        super("கருவிளநறும்பூ");
    }
    public String generate(FeatureSet featureSet) {
        return  "(?:(?:${ntirai}){3}${ntear})";

    }
}