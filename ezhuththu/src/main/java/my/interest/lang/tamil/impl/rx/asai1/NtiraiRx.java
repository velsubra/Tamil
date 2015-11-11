package my.interest.lang.tamil.impl.rx.asai1;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;
import tamil.lang.api.regex.RXFixedLengthFeature;
import tamil.lang.api.regex.RXKuttuFeature;

/**
 * <p>
 *     நிரையசைக்கான செங்கோவை
 * </p>
 *
 * @author velsubra
 */
public  class NtiraiRx  extends YaappuBaseRx {

    protected NtiraiRx(String name) {
        super(name);
    }
    public NtiraiRx() {
        super("நிரை");
    }

    public String generate(FeatureSet featureSet) {
        String fixed = null;
        if (featureSet.isFeatureEnabled(RXFixedLengthFeature.class)) {
            fixed = "{0,2}";
        } else {
            fixed = "*";
        }
            return "(?:(?:${orumaaththirai}${irumaaththirai}${araimaaththirai}"+fixed+")|(?:(${orumaaththirai}){2}${araimaaththirai}"+fixed+"))";
    }
}