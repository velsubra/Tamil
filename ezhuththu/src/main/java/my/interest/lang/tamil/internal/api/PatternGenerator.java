package my.interest.lang.tamil.internal.api;

import my.interest.lang.tamil.impl.FeatureSet;
import tamil.lang.api.ezhuththu.EzhuththuSetDescription;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public interface PatternGenerator extends EzhuththuSetDescription {
    //public static  int LAST_UNICODE_CODEPOINT = 1114111;
    //BMP
    public static  int LAST_UNICODE_CODEPOINT = 65535;
    public  String generate(FeatureSet set);



}
