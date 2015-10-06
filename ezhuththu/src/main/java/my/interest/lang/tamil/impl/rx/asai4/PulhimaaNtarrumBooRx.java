package my.interest.lang.tamil.impl.rx.asai4;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class PulhimaaNtarrumBooRx  extends YaappuBaseRx {

    public PulhimaaNtarrumBooRx() {
        super("புளிமாநறும்பூ");
    }
    public String generate(FeatureSet featureSet) {
        return  "(?:(?:${ntirai}${ntear}){2})";

    }
}