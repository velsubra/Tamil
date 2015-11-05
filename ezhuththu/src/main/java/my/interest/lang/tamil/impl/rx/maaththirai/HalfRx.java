package my.interest.lang.tamil.impl.rx.maaththirai;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.rx.ORPatternGenerator;
import tamil.lang.TamilCharacter;
import tamil.lang.api.regex.RXAythamAsKurrilFeature;
import tamil.lang.api.regex.RXKuttuAcrossCirFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class HalfRx extends ORPatternGenerator {



    public List<String> getList(FeatureSet set) {


        List<String> patterns = new ArrayList<String>();
        patterns.add("${மெய்}");
        if (!set.isFeatureEnabled(RXAythamAsKurrilFeature.class)) {
            patterns.add("${ஆய்தம்}");
        }

        if (set.isFeatureEnabled(RXKuttuAcrossCirFeature.class)) {
            patterns.add("${பிரிக்கப்பட்ட குற்று}");
        }


        return patterns;


    }

    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }


    public String getName() {
        return "அரைமாத்திரை";
    }

    public String getDescription() {
        return "அரைமாத்திரைகொண்டு ஒலிக்கும் பாங்கு";
    }
}
