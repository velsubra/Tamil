package my.interest.lang.tamil.impl.rx.asai1;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;
import tamil.lang.api.regex.RXKuttuFeature;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public  class NtearRx extends YaappuBaseRx {

    protected NtearRx(String name) {
        super(name);
    }

    public NtearRx() {
        super("நேர்");
    }

    public String generate(FeatureSet featureSet) {

            return "(?!(${ntirai}))(?:(?:${irumaaththirai}${araimaaththirai}*)|(?:${orumaaththirai}${araimaaththirai}*))";
    }
}