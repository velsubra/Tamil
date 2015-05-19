package tamil.lang.known.non.derived;

import tamil.lang.TamilCompoundCharacter;
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
    public static final Ottu IK = new Ottu( new TamilWord(TamilCompoundCharacter.IK));
    public static final Ottu ICH = new Ottu( new TamilWord(TamilCompoundCharacter.ICH));
    public static final Ottu ITH = new Ottu( new TamilWord(TamilCompoundCharacter.ITH));
    public static final Ottu IP = new Ottu( new TamilWord(TamilCompoundCharacter.IP));

    private Ottu(TamilWord val) {
        super(val, true);
    }
}
