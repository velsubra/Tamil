package my.interest.lang.tamil.impl.rx.asai3;

import my.interest.lang.tamil.impl.yaappu.AsaiRx;
import my.interest.lang.tamil.internal.api.PatternGenerator;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class PulhimaanganiRx extends AsaiRx {

    public PulhimaanganiRx() {
        super("புளிமாங்கனி");
    }
    public String generate() {
        return  "((?!(${karuvilham}))(${ntirai}${ntear}${ntirai})|(${ntirai}${kurril}${ottu}${ntirai}))";
    }
}