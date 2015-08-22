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
public abstract  class AsaiRx implements PatternGenerator {

    public String getName() {
        return name;
    }

    String name = null;

    public AsaiRx(String name) {
       this.name = name;
    }

    public String getDescription() {
        return name;
    }

    public boolean isCharacterSet() {
        return false;
    }

    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }
}
