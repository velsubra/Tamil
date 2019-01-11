package my.interest.lang.tamil.impl.rx.paa;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;

import java.util.Set;


public abstract class AbstractVenhbaThalhaiRx implements PatternGenerator {

    private final int lineCount;

    AbstractVenhbaThalhaiRx(int lineCount) {
        this.lineCount = lineCount;
    }

    public String generate(FeatureSet featureSet) {
        return "(?:${வெண்டளை}{" + (4 * lineCount - 2) + "})";
    }

    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }

    public String getName() {
        return "வெண்பாவின் தளையமைப்பு";
    }

    public String getDescription() {
        return "தளைகளின் அமைப்புவழியாக வெண்பாவின் பாங்கு";
    }


}
