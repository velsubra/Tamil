package my.interest.lang.tamil.impl.rx.thalhai;

import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;

import java.util.Set;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final class IyarrCirVendalhaiRx implements PatternGenerator {
    public String generate() {
        return "${" + new MaaMunNtiraiRx().getName() +"}|${"+new VilhamMunNtear().getName()+"}";
    }


    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }


    public String getName() {
        return "இயற்சீர்வெண்டளை";
    }


    public String getDescription() {
        return "இயற்சீர்வெண்டளை";
    }
}
