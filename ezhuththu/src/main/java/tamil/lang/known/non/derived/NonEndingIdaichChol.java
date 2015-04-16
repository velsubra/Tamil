package tamil.lang.known.non.derived;

import tamil.lang.TamilWord;
import my.interest.lang.tamil.translit.EnglishToTamilCharacterLookUpContext;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class NonEndingIdaichChol extends AbstractKnownWord implements INonEndingIdaichchol {


    public NonEndingIdaichChol(TamilWord word, boolean atomic) {
        super(word);
        this.atomic = atomic;
        if (atomic) {
            addProperty("THANI", "true");
        }

        type = EnglishToTamilCharacterLookUpContext.getBestMatch("idaichchol");
        addProperty("MUDI", "false");
    }

    private boolean atomic = false;
    @Override
    public boolean isAtomic() {
        return atomic;
    }
}
