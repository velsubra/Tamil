package my.interest.lang.tamil.impl.rx.asai4;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class KoovizhanthanhnhizhalRx  extends YaappuBaseRx {

    public KoovizhanthanhnhizhalRx() {
        super("கூவிளந்தண்ணிழல்");
    }
    public String generate(FeatureSet featureSet) {
        return  "(?:(?:${ntear}${ntirai}){2})";

    }
}