package my.interest.lang.tamil.impl.rx.paa;

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
public class KurralhThalaiRx implements PatternGenerator {
    public String generate(FeatureSet featureSet) {
        return "(?:${வெண்டளை}{6})";
    }


    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }


    public String getName() {
        return "குறளின் தளையமைப்பு";
    }

    public String getDescription() {
        return "தளைகளின் அமைப்புவழியாக திருக்குறளின் பாங்கு";
    }
}
