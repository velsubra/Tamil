package my.interest.lang.tamil.impl.rx.util;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;

import java.util.Set;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class IdaivelhiRx implements PatternGenerator {
    public String generate(FeatureSet featureSet) {
        return "(?:\\s+)";
    }


    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }


    public String getName() {
        return "இடைவெளி";
    }

    public String getDescription() {
        return "One or more white spaces.  space or new line etc..";
    }
}
