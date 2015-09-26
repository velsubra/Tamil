package my.interest.lang.tamil.impl.rx.maaththirai;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;
import tamil.lang.api.regex.RXKuttuFeature;
import tamil.lang.api.regex.RXAythamAsKurrilFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class OneRx implements PatternGenerator {
    public String generate(FeatureSet set) {
        List<String> patterns = new ArrayList<String>();
        patterns.add("குறில்");
        if (set.isFeatureEnabled(RXAythamAsKurrilFeature.class)) {
            patterns.add("ஆய்தம்");
        }
        if (set.isFeatureEnabled(RXKuttuFeature.class)) {
            patterns.add("குற்றுக்குறில்");
        }
        StringBuffer buffer = new StringBuffer();
        if (patterns.size() > 1) {
            buffer.append("(?:");
        }
        boolean first = true;
        for (String rx : patterns) {
            if (!first) {
                buffer.append("|");
            }
            first = false;
            buffer.append("${" + rx +"}");

        }
        if (patterns.size() > 1) {
            buffer.append(")");
        }
        return buffer.toString();

//
//            return "(?:${குறில்}|${ஆய்தம்})";
//
//        } else {
//            return "${குறில்}";
//        }

    }

    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }


    public String getName() {
        return "ஒருமாத்திரை";
    }

    public String getDescription() {
        return "ஒருமாத்திரைகொண்டு ஒலிக்கும் பாங்கு";
    }
}
