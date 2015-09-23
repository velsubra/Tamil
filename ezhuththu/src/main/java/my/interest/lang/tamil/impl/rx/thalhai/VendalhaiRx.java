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
public class VendalhaiRx implements PatternGenerator {
    public String generate() {
        return "(${" + new IyarrCirVendalhaiRx().getName() +"}|${"+new VenhCirVendalhaiRx().getName()+"})";
    }


    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }


    public String getName() {
        return "வெண்டளை";
    }


    public String getDescription() {
        return "இயற்சீர்வெண்டளை அல்லது வெண்சீர்வெண்டளை";
    }
}


