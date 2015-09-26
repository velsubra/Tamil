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
public class KurralRx implements PatternGenerator {
    public String generate(FeatureSet featureSet) {
        return "(?:(?=${குறளின் தளையமைப்பு})${குறளின் சீரமைப்பு})" ;
    }


    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }


    public String getName() {
        return "குறள்";
    }

    public String getDescription() {
        return "குறளுக்கான பாங்கு. சீர்களின் அமைப்பையும்  தளைகளின் அமைப்பையும் உள்ளடக்குகிறது.    ";
    }
}