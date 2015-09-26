package my.interest.lang.tamil.impl.rx.asai1;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public   class NtiraibuRx extends YaappuBaseRx {

    protected NtiraibuRx(String name) {
        super(name);
    }

    public NtiraibuRx() {
        super("நிரைபு");
    }

    public String generate(FeatureSet featureSet) {
        return  "(?:${ntirai}${valiyugaravarisai})";
    }
}
