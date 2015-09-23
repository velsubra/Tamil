package my.interest.lang.tamil.impl.rx.asai3;

import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class KoovilhanganiRx  extends YaappuBaseRx {

    public KoovilhanganiRx() {
        super("கூவிளங்கனி");
    }
    public String generate() {

        return  "((?!(${ntirai}))(${ntear}${ntirai}${ntirai})|(${kurril}${mey}+${ntirai}${ntirai})|(${ntedil}${mey}+${ntirai}${ntirai}))";
    }
}