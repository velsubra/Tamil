package my.interest.lang.tamil.impl.rx.paa;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;

import java.util.Set;

public class FourLineVenhbaRx implements PatternGenerator {

    private AbstractVenhbaCirRx cirRx = new AbstractVenhbaCirRx(4) {
    };
    private AbstractVenhbaThalhaiRx thalhaiRx = new AbstractVenhbaThalhaiRx(4) {
    };

    public String generate(FeatureSet featureSet) {
        return "(?:(?=" + thalhaiRx.generate(featureSet) + ")" + cirRx.generate(featureSet) + ")";
    }


    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }


    public String getName() {
        return "4அடிவெண்பா";
    }

    public String getDescription() {
        return "4அடிவெண்பாக்கா பாங்கு. சீர்களின் அமைப்பையும்  தளைகளின் அமைப்பையும் உள்ளடக்குகிறது.    ";
    }
}