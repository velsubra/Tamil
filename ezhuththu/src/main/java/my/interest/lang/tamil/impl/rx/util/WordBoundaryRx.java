package my.interest.lang.tamil.impl.rx.util;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;

import java.util.Set;

/**
 * Created by velsubra on 11/29/15.
 */
public class WordBoundaryRx implements PatternGenerator {
    public String generate(FeatureSet set) {
        return "(?:\\b|(?:(?=" + new IdaivelhiRx().generate(set)+")))";
    }

    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }

    public String getName() {
        return "சொல்லெல்லை";
    }

    public String getDescription() {
        return "சொல்லின் எல்லையைக்குறிக்கிறது.    (0 width)";
    }
}
