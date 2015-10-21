package my.interest.lang.tamil.impl.rx;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;
import tamil.lang.api.regex.RXFixedLengthFeature;

import java.util.Set;

/**
 * <p>
 * Regular expression for matching any Tamil word. ${மொழி} can be used in any Tamil regular expression.
 * <p/>
 * ${மொழி} is defined to be (${ஓரெழுத்துமொழி})|(${மொழிமுதல்}${மொழியிடை}*${மொழிக்கடை}+)
 * <p/>
 * </p>
 *
 * @author velsubra
 */
public class MozhiRx implements PatternGenerator {
    public String generate(FeatureSet featureSet) {
        String fixedStar = null;
        String fixedPlus = null;
        if (featureSet.isFeatureEnabled(RXFixedLengthFeature.class)) {
            fixedStar = "{0,20}";
            fixedPlus = "{1,20}";
        } else {
            fixedStar = "*";
            fixedPlus = "+";
        }

        return "(?:(?:${mozhimuthal}${mozhiyidai}" + fixedStar + "${mozhikkadai}" + fixedPlus + ")|${oarezhuththumozhi})";
    }

    public String getName() {
        return "மொழி";
    }

    public String getDescription() {
        return "Pattern for a Tamil word. A possible sequence that could be a Tamil Word.";
    }


    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }
}