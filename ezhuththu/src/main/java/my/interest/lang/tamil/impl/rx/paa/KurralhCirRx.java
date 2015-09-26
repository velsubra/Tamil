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
public class KurralhCirRx implements PatternGenerator {
    public String generate(FeatureSet featureSet) {
        return "(?:(?:(?:${மாச்சீர்}|${விளச்சீர்}|${காய்ச்சீர்})${இடைவெளி}){6}${வெண்பாவின் இறுதிச்சீர்})";
    }


    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }


    public String getName() {
        return "குறளின் சீரமைப்பு";
    }

    public String getDescription() {
        return "சீர்களின் அமைப்புவழியாக திருக்குறளின் பாங்கு";
    }
}
