package my.interest.lang.tamil.impl.rx.asai3;

import my.interest.lang.tamil.impl.yaappu.AsaiRx;
import my.interest.lang.tamil.internal.api.PatternGenerator;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class KoovilhanganiRx  extends AsaiRx {

    public KoovilhanganiRx() {
        super("கூவிளங்கனி");
    }
    public String generate() {

        return  "((?!(${ntirai}))(${ntear}${ntirai}${ntirai})|(${kurril}${ottu}${ntirai}${ntirai}))";
    }
}