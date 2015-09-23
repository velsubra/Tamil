package my.interest.lang.tamil.impl.yaappu;

import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;

import java.util.Set;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public abstract  class YaappuBaseRx implements PatternGenerator {

    public String getName() {
        return name;
    }

    String name = null;
    String desc = null;

    public YaappuBaseRx(String name) {
       this.name = name;
       this.desc = "வாய்ப்பாடு '"+ this.name + "' -ஐக்குறிக்கிறது.";
    }

    public String getDescription() {
        return desc;
    }



    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }
}
