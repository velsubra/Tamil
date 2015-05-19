package tamil.lang.known.non.derived;

import tamil.lang.TamilWord;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class Thaan extends AtomicIsolatedIdai implements INonStartingIdaichchol{
    public static final Thaan THAAN = new Thaan();
    private Thaan() {
        super(TamilWord.from("தான்"));
    }

}