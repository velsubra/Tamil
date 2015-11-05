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
public class IgarakkuttuNtedil implements PatternGenerator {
    public String generate(FeatureSet featureSet) {
        return "(?:${வலியுகரவரிசை}${யகரவரிசையுயிர்மெய்நெடில்})";
    }


    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }


    public String getName() {
        return "இகரக்குற்றுநெடில்";
    }


    public String getDescription() {
        return "டுயா   என்பது \"டியா\" என குற்றியலிகரமாக கணக்கிடப்பட்டு நேரசையாகக்கருதப்படும்.        ";
    }
}
