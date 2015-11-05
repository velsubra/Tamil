package my.interest.lang.tamil.impl.rx.maaththirai;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.rx.ORPatternGenerator;
import my.interest.lang.tamil.impl.rx.TempORPatternGeneratorImpl;
import my.interest.lang.tamil.impl.rx.asai1.Kuttukkurril;
import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;
import tamil.lang.api.regex.RXAythamAsKurrilFeature;
import tamil.lang.api.regex.RXKuttuAcrossCirFeature;
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
public class OneRx extends ORPatternGenerator {
    public List<String> getList(FeatureSet set) {
        List<String> patterns = new ArrayList<String>();
        List<String> tobeNotStartingWith = new ArrayList<String>();

        if (set.isFeatureEnabled(RXKuttuAcrossCirFeature.class)) {
            tobeNotStartingWith.add(new SplitKuttuRX().generate(set));

        }
        if (set.isFeatureEnabled(RXKuttuFeature.class)) {
            patterns.add("${குற்றுக்குறில்}");
            tobeNotStartingWith.add("${குற்றுக்குறில்}");
            tobeNotStartingWith.add("${குற்றுநெடில்}");
        }
        if (tobeNotStartingWith.isEmpty()) {
            patterns.add("${குறில்}");
        } else {
            patterns.add("(?:(?!(?:"+ new TempORPatternGeneratorImpl(tobeNotStartingWith).generate(set) +"))${குறில்})");
        }




        if (set.isFeatureEnabled(RXAythamAsKurrilFeature.class)) {
            patterns.add("${ஆய்தம்}");
        }

        return patterns;

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
