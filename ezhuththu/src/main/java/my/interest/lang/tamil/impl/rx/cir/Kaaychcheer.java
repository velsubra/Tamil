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
public class Kaaychcheer extends AbstractCirCollectionRx {
    @Override
    public List<String> getAllCirs() {
        return Arrays.asList(new String[]{"தேமாங்காய்", "புளிமாங்காய்","கூவிளங்காய்", "கருவிளங்காய்"});
    }


    public String getName() {
        return "காய்ச்சீர்";
    }


    public String getDescription() {
        return "எதேனுமொரு காய்ச்சீரைக்குறிக்கிறது. ";
    }
}