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
public class NtizharrSeer extends AbstractCirCollectionRx {
    @Override
    public List<String> getAllCirs() {
        return Arrays.asList(new String[]{"தேமாந்தண்ணிழல்", "தேமாநறுநிழல்"
                , "புளிமாந்தண்ணிழல்", "புளிமாநறுநிழல்"
                , "கூவிளந்தண்ணிழல்", "கூவிளநறுநிழல்",
                "கருவிளந்தண்ணிழல்", "கருவிளநறுநிழல்"});
    }


    public String getName() {
        return "நிழற்சீர்";
    }


    public String getDescription() {
        return "எதேனுமொரு நிழற்சீரைக்குறிக்கிறது. ";
    }
}