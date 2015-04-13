package tamil.lang.known.non.derived;

import tamil.lang.TamilWord;

/**
 * <p>
 * ஒற்றெழுத்தைக்குறிப்பது
 * எ.கா)
 * க்,ச்,ட்,த்
 * </p>
 *
 * @author velsubra
 */
public final class Ottu extends NonStartingIdaichchol implements INonEndingIdaichchol {
    public Ottu(String val) {
        super(TamilWord.from(val), true);
    }
}
