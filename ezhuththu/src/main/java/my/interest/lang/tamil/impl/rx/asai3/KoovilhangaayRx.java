package my.interest.lang.tamil.impl.rx.asai3;

import my.interest.lang.tamil.impl.yaappu.AsaiRx;
import my.interest.lang.tamil.internal.api.PatternGenerator;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class KoovilhangaayRx extends AsaiRx {

    public KoovilhangaayRx() {
        super("கூவிளங்காய்");
    }
    public String generate() {

        return  "((?!(${ntirai}))(${ntear}${ntirai}${ntear})|(${kurril}${ottu}${ntirai}${ntear}))";
    }
}