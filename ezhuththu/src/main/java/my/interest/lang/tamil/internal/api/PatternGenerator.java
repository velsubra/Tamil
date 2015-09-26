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

    public  String generate(FeatureSet set);



}
