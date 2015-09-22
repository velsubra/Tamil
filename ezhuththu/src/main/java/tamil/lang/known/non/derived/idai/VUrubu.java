package tamil.lang.known.non.derived.idai;

import my.interest.lang.tamil.translit.EnglishToTamilCharacterLookUpContext;
import tamil.lang.TamilWord;
import tamil.lang.known.non.derived.NonStartingIdaichchol;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class VUrubu extends NonStartingIdaichchol {
    public VUrubu(TamilWord word, boolean atomic, int number) {
        super(word, atomic);
        this.number = number;
        type = EnglishToTamilCharacterLookUpContext.getBestMatch("veattumaiyurubu");
    }

    public int getNumber() {
        return number;
    }

    private int number;
}
