package my.interest.lang.tamil.impl.rx.maaththirai;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;
import tamil.lang.api.regex.RXAythamAsKurrilFeature;
import tamil.lang.api.regex.RXOverrideSysDefnFeature;

import java.util.Set;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class HalfRx implements PatternGenerator {
    public String generate(FeatureSet set) {
        if (set.isFeatureEnabled(RXAythamAsKurrilFeature.class)) {
            return "${மெய்}";
        } else {
            return "(?:${மெய்}|${ஆய்தம்})";
        }

    }

    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }


    public String getName() {
        return "அரைமாத்திரை";
    }

    public String getDescription() {
        return "அரைமாத்திரைகொண்டு ஒலிக்கும் பாங்கு";
    }
}
