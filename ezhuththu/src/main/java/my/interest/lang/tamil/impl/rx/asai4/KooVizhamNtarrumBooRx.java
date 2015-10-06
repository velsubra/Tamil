package my.interest.lang.tamil.impl.rx.asai4;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class KooVizhamNtarrumBooRx extends YaappuBaseRx {

    public KooVizhamNtarrumBooRx() {
        super("கூவிளநறும்பூ");
    }
    public String generate(FeatureSet featureSet) {
        return  "(?:${ntear}(?:${ntirai}){2}${ntear})";

    }
}