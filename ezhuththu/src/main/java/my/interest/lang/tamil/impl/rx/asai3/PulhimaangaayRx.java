package my.interest.lang.tamil.impl.rx.asai3;

import my.interest.lang.tamil.impl.yaappu.AsaiRx;
import my.interest.lang.tamil.internal.api.PatternGenerator;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class PulhimaangaayRx extends AsaiRx {

    public PulhimaangaayRx() {
        super("புளிமாங்காய்");
    }
    public String generate() {
        return  "((?!(${karuvilham}))(${ntirai}${ntear}${ntear})|(${ntirai}${kurril}${ottu}${ntear}))";
    }
}