package tamil.lang.known.non.derived;

import tamil.lang.TamilWord;

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
    }

    public int getNumber() {
        return number;
    }

    private int number;
}
