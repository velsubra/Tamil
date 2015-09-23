package my.interest.lang.tamil.impl.rx.cir;

import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;

import java.util.Set;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class VenhbaLastCirRx implements PatternGenerator {
    public String generate() {
        return "(${நாள்}|${மலர்}|${காசு}|${பிறப்பு})";
    }


    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }


    public String getName() {
        return "வெண்பாவின் இறுதிச்சீர்";
    }

    public String getDescription() {
        return "நாள் ,  மலர் ,  காசு ,  பிறப்பு  ஆகியவற்றில் ஏதேனுமொன்றின் வாய்ப்பாட்டைக்குறிக்கிறது.  ";
    }
}
