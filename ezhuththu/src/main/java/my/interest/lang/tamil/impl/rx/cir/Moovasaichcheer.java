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
public class Moovasaichcheer extends AbstractCirCollectionRx {
    @Override
    public List<String> getAllCirs() {
        return Arrays.asList(new String[]{"தேமாங்காய்", "புளிமாங்காய்","கூவிளங்காய்", "கருவிளங்காய்","தேமாங்கனி", "புளிமாங்கனி", "கூவிளங்கனி", "கருவிளங்கனி"});
    }


    public String getName() {
        return "மூவசைச்சீர்";
    }

    public String generate(FeatureSet set) {
        return new NAsaichCheer(3,3).generate(set);
    }


    public String getDescription() {
        return "எதேனுமொரு மூவசைச்சீரைக்குறிக்கிறது. ";
    }
}