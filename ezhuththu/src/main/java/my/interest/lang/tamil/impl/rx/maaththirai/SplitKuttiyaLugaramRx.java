package my.interest.lang.tamil.impl.rx.maaththirai;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.rx.ORPatternGenerator;
import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class SplitKuttiyaLugaramRx extends  ORPatternGenerator {
    @Override
    public List<String> getList(FeatureSet set) {
        List<String> list = new ArrayList<String>();
        list.add("(?:\\(${valiyugaravarisai}\\)(?=(?:${idaivelhi}${uyir})))");
        list.add("(?:${valiyugaravarisai}(?=(?:${idaivelhi}${uyir})))");

        return list;
    }



    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }


    public String getName() {
        return "பிரிக்கப்பட்ட குற்றியலுகரம்";
    }

    public String getDescription() {
        return "உயிரெழுத்துடன் தொடங்கும் சொல்லின் முன்னாலிருக்குஞ்சொல்லின் முடிவிலிருக்கும் குற்றியலுகரம்  ";
    }
}
