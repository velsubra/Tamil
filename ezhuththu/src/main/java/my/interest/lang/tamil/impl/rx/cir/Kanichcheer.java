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
public class Kanichcheer extends AbstractCirCollectionRx {
    @Override
    public List<String> getAllCirs() {
        return Arrays.asList(new String[]{"தேமாங்கனி", "புளிமாங்கனி", "கூவிளங்கனி", "கருவிளங்கனி"});
    }


    public String getName() {
        return "கனிச்சீர்";
    }


    public String getDescription() {
        return "எதேனுமொரு கனிச்சீரைக்குறிக்கிறது. ";
    }
}