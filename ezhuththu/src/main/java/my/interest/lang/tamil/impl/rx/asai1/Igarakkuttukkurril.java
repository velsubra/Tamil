package my.interest.lang.tamil.impl.rx.asai1;

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
public class Igarakkuttukkurril implements PatternGenerator {
    public String generate(FeatureSet featureSet) {
        return "(?:${வலியுகரவரிசை}${யகரவரிசையுயிர்மெய்க்குறில்})";
    }


    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }


    public String getName() {
        return "இகரக்குற்றுக்குறில்";
    }


    public String getDescription() {
        return "டுயக    என்பது \"டியக\" என குற்றியலிகரமாக கணக்கிடப்பட்டு ஒரு நிரையசையாகக்கொள்ளப்படும்.";
    }
}
