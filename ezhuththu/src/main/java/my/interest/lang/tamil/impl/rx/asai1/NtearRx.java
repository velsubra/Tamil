package my.interest.lang.tamil.impl.rx.asai1;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;
import tamil.lang.api.regex.RXFixedLengthFeature;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class NtearRx extends YaappuBaseRx {

    protected NtearRx(String name) {
        super(name);
    }

    public NtearRx() {
        super("நேர்");
    }

    public String generate(FeatureSet featureSet) {
        String fixed = null;
        if (featureSet.isFeatureEnabled(RXFixedLengthFeature.class)) {
            fixed = "{0,2}";
        } else {
            fixed = "*";
        }


        return "(?!(${ntirai}))(?:(?:${irumaaththirai}${araimaaththirai}"+ fixed +")|(?:${orumaaththirai}${araimaaththirai}"+ fixed +"))";
    }
}