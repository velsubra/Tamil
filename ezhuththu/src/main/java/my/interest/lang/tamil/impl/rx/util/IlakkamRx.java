package my.interest.lang.tamil.impl.rx.util;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;
import tamil.lang.api.regex.RXFixedLengthFeature;

import java.util.Set;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class IlakkamRx implements PatternGenerator {
    public String generate(FeatureSet featureSet) {

            return "\\d";

    }


    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }


    public String getName() {
        return "இலக்கம்";
    }

    public String getDescription() {
        return "Represents a digit.";
    }
}
