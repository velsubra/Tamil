package my.interest.lang.tamil.impl.rx.cir;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;

import java.util.Set;

/**
 * Created by velsubra on 6/29/16.
 */
public class VenhbaaCirRx implements PatternGenerator {
    public String generate(FeatureSet featureSet) {
        return "(?:${மாச்சீர்}|${விளச்சீர்}|${காய்ச்சீர்})";
    }


    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }


    public String getName() {
        return "வெண்பாவின் சீர்";
    }

    public String getDescription() {
        return "மா, விளம், காய் ஆகியவற்றில் ஒருசீரைக்குறிக்கிறது.  ";
    }
}
