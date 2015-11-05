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
public class SplitKuttiyaIigaramRx implements PatternGenerator {
    public String generate(FeatureSet set) {
        return "(?:${valiyigaravarisai}(?=(?:${idaivelhi}${yagaravarisaiyuyirmei})))";
    }

    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }


    public String getName() {
        return "பிரிக்கப்பட்ட குற்றியலிகரம்";
    }

    public String getDescription() {
        return "யகரவரிசையுயிர்மெய்யில் தொடங்கும் சொல்லின் முன்னாலிருக்குஞ்சொல்லின் முடிவிலிருக்கும் குற்றியலிகரம்  ";
    }
}
