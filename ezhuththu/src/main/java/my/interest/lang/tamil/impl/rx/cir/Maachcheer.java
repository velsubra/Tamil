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
public class Maachcheer extends AbstractCirCollectionRx {
    @Override
    public List<String> getAllCirs() {
        return Arrays.asList(new String[]{"தேமா", "புளிமா"});
    }


    public String getName() {
        return "மாச்சீர்";
    }


    public String getDescription() {
        return "எதேனுமொரு மாச்சீரைக்குறிக்கிறது. ";
    }
}
