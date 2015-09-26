package my.interest.lang.tamil.impl.rx.asai3;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class KaruvilhangaayRx extends YaappuBaseRx {

    public KaruvilhangaayRx() {
        super("கருவிளங்காய்");
    }

    public String generate(FeatureSet featureSet) {
        return "(?:${ntirai}{2}${ntear})";
    }
}