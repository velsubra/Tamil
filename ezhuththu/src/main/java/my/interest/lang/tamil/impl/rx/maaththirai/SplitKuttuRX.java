package my.interest.lang.tamil.impl.rx.maaththirai;

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
public class SplitKuttuRX implements PatternGenerator {

    public String generate(FeatureSet set) {
        return "(?:"+ new SplitKuttiyaLugaramRx().generate(set) +"|"+ new SplitKuttiyaIigaramRx().generate(set)+")";
    }

    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }


    public String getName() {
        return "பிரிக்கப்பட்ட குற்று";
    }

    public String getDescription() {
        return "பிரிக்கப்பட்ட குற்றியலிகரம் அல்லது   பிரிக்கப்பட்ட குற்றியலுகரம்";
    }

}
