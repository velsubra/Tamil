package my.interest.lang.tamil.impl.rx.asai4;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class KoovilhanthanhBooRx extends YaappuBaseRx {

    public KoovilhanthanhBooRx() {
        super("கூவிளந்தண்பூ");
    }
    public String generate(FeatureSet featureSet) {
        return  "(?:${ntear}${ntirai}(?:${ntear}){2})";

    }
}