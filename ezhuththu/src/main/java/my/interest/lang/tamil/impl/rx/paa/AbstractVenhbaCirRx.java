package my.interest.lang.tamil.impl.rx.paa;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;

import java.util.Set;


public abstract class AbstractVenhbaCirRx implements PatternGenerator {
    private final int lineCount;

    AbstractVenhbaCirRx(int lineCount) {
        this.lineCount = lineCount;
    }

    public String generate(FeatureSet featureSet) {
        return "(?:(?:${வெண்பாவின் சீர்}${இடைவெளி}){" + (4 * lineCount - 2) + "}${வெண்பாவின் இறுதிச்சீர்})";
    }

    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }

    public String getName() {
        return "வெண்பாவின் சீரமைப்பு";
    }

    public String getDescription() {
        return "சீர்களின் அமைப்புவழியாக வெண்பாவின் பாங்கு";
    }

}
