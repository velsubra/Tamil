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
public class Asai extends AbstractCirCollectionRx {
    @Override
    public List<String> getAllCirs() {
        return Arrays.asList(new String[]{"நிரை", "நேர்"});
    }


    public String getName() {
        return "அசை";
    }


    public String getDescription() {
        return "ஏதேனும் ஒர் அசையைக்குறிக்கிறது.";
    }
}
