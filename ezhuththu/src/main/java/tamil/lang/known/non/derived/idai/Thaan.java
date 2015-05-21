package tamil.lang.known.non.derived.idai;

import tamil.lang.TamilWord;
import tamil.lang.known.non.derived.AtomicIsolatedIdai;
import tamil.lang.known.non.derived.INonStartingIdaichchol;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class Thaan extends AtomicIsolatedIdai implements INonStartingIdaichchol {
    public static final Thaan THAAN = new Thaan();

    private Thaan() {
        super(TamilWord.from("தான்"));
    }

}