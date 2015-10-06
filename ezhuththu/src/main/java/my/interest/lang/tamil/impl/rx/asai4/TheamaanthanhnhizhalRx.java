package my.interest.lang.tamil.impl.rx.asai4;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TheamaanthanhnhizhalRx extends YaappuBaseRx {

    public TheamaanthanhnhizhalRx() {
        super("தேமாந்தண்ணிழல்");
    }
    public String generate(FeatureSet featureSet) {
        return  "(?:(?:${ntear}){3}${ntirai})";
        //return  "(?:(?!(?:(?:${ntirai})|${koovilham}|${theamaangani}).*)(?:${ntear}{3}${ntirai}))";
    }
}