package my.interest.lang.tamil.impl.rx.asai4;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class PulhimaanthanhnhizhalRx  extends YaappuBaseRx {

    public PulhimaanthanhnhizhalRx() {
        super("புளிமாந்தண்ணிழல்");
    }
    public String generate(FeatureSet featureSet) {
        return  "(?:${ntirai}(?:${ntear}){2}${ntirai})";

    }
}