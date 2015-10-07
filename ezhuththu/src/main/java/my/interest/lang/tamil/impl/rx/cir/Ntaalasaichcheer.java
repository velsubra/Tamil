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
public class Ntaalasaichcheer extends AbstractCirCollectionRx {
    @Override
    public List<String> getAllCirs() {
        return Arrays.asList(new String[]{"தேமாந்தண்பூ", "தேமாந்தண்ணிழல்", "தேமாநறும்பூ", "தேமாநறுநிழல்"
                ,"புளிமாந்தண்பூ","புளிமாந்தண்ணிழல்","புளிமாநறும்பூ","புளிமாநறுநிழல்"
        ,"கூவிளந்தண்பூ","கூவிளந்தண்ணிழல்","கூவிளநறும்பூ","கூவிளநறுநிழல்",
        "கருவிளந்தண்பூ","கருவிளந்தண்ணிழல்","கருவிளநறும்பூ","கருவிளநறுநிழல்"});
    }


    public String getName() {
        return "நாலசைச்சீர்";
    }


    public String generate(FeatureSet set) {
        return new NAsaichCheer(4,4).generate(set);
    }


    public String getDescription() {
        return "எதேனுமொரு நாலசைச்சீரைக்குறிக்கிறது. ";
    }
}