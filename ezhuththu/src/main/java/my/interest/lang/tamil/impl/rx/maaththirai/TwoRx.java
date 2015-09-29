package my.interest.lang.tamil.impl.rx.maaththirai;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.rx.ORPatternGenerator;
import tamil.lang.TamilCharacter;
import tamil.lang.api.regex.RXKuttuFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TwoRx extends ORPatternGenerator {
    public List<String> getList(FeatureSet set) {
        List<String> patterns = new ArrayList<String>();
        patterns.add("${நெடில்}");

        if (set.isFeatureEnabled(RXKuttuFeature.class)) {
            patterns.add("${குற்றுநெடில்}");
        }
        return patterns;
    }


    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }

    public String getName() {
        return "இருமாத்திரை";
    }

    public String getDescription() {
        return "இருமாத்திரைகொண்டு ஒலிக்கும் பாங்கு";
    }
}