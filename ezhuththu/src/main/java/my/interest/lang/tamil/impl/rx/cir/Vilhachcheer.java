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
public class Vilhachcheer extends AbstractCirCollectionRx {
    @Override
    public List<String> getAllCirs() {
        return Arrays.asList(new String[]{"கூவிளம்", "கருவிளம்"});
    }


    public String getName() {
        return "விளச்சீர்";
    }


    public String getDescription() {
        return "எதேனுமொரு விளச்சீரைக்குறிக்கிறது. ";
    }
}
