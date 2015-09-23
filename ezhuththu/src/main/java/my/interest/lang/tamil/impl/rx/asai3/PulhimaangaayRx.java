package my.interest.lang.tamil.impl.rx.asai3;

import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class PulhimaangaayRx extends YaappuBaseRx {

    public PulhimaangaayRx() {
        super("புளிமாங்காய்");
    }
    public String generate() {
        return  "((?!(${karuvilham}))(${ntirai}${ntear}${ntear})|(${ntirai}${kurril}${mey}${ntear}))";
    }
}