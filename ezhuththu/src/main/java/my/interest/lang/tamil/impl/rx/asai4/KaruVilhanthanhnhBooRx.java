package my.interest.lang.tamil.impl.rx.asai4;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class KaruVilhanthanhnhBooRx  extends YaappuBaseRx {

    public KaruVilhanthanhnhBooRx() {
        super("கருவிளந்தண்பூ");
    }
    public String generate(FeatureSet featureSet) {
        return  "(?:(?:${ntirai}){2}(?:${ntear}){2})";

    }
}