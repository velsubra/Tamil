package tamil.lang.known.non.derived;

import tamil.lang.TamilWord;
import my.interest.lang.tamil.translit.EnglishToTamilCharacterLookUpContext;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class NonStartingIdaichchol extends AbstractKnownWord implements  INonStartingIdaichchol {
    private boolean atomic = false;
    public NonStartingIdaichchol(TamilWord word, boolean atomic) {
        super(word);
        this.atomic = atomic;
        if (atomic) {
            addProperty("THANI", "true");
        }
        type = EnglishToTamilCharacterLookUpContext.getBestMatch("idaichchol");
        addProperty("THALAI", "false");
    }


    @Override
    public boolean isAtomic() {
        return this.atomic;
    }
}
