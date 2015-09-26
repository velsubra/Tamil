package my.interest.lang.tamil.impl.rx.asai3;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class KaruvilhanganiRx extends YaappuBaseRx {

    public KaruvilhanganiRx() {
        super("கருவிளங்கனி");
    }
    public String generate(FeatureSet featureSet) {
        return  "(?:${ntirai}{3})";
    }
}