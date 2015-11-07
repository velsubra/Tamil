package my.interest.lang.tamil.impl.rx.asai1;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.rx.ORPatternGenerator;
import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;
import tamil.lang.api.regex.RXDesolvedKuttialigaramFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class IgarakkuttuNtedil extends ORPatternGenerator {
    @Override
    public List<String> getList(FeatureSet set) {
        List<String> list = new ArrayList<String>();
        if (set.isFeatureEnabled(RXDesolvedKuttialigaramFeature.class)) {
            list.add("(?:${வலியிகரவரிசை}${யகரவரிசையுயிர்மெய்நெடில்})");
        }
        list.add("(?:${வலியுகரவரிசை}${யகரவரிசையுயிர்மெய்நெடில்})");
        return list;
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
