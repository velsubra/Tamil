package my.interest.lang.tamil.impl.rx.cir;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.yaappu.AbstractCirCollectionRx;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class Eerasaichcheer extends AbstractCirCollectionRx {
    @Override
    public List<String> getAllCirs() {
        return Arrays.asList(new String[]{"தேமா", "புளிமா", "கூவிளம்", "கருவிளம்"});
    }


    public String getName() {
        return "ஈரசைச்சீர்";
    }

    public String generate(FeatureSet set) {
        return new NAsaichCheer(2,2).generate(set);
    }


    public String getDescription() {
        return "ஏதேனும் ஒர் ஈரசைச்சீரைக்குறிக்கிறது. ";
    }
}