package my.interest.lang.tamil.impl.rx.cir;

import my.interest.lang.tamil.impl.yaappu.AbstractCirCollectionRx;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class Poochcheer extends AbstractCirCollectionRx {
    @Override
    public List<String> getAllCirs() {
        return Arrays.asList(new String[]{"தேமாந்தண்பூ", "தேமாநறும்பூ"
                , "புளிமாந்தண்பூ", "புளிமாநறும்பூ"
                , "கூவிளந்தண்பூ", "கூவிளநறும்பூ",
                "கருவிளந்தண்பூ", "கருவிளநறும்பூ"});
    }


    public String getName() {
        return "பூச்சீர்";
    }


    public String getDescription() {
        return "எதேனுமொரு பூச்சீரைக்குறிக்கிறது. ";
    }
}
