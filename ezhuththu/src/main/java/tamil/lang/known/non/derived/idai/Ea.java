package tamil.lang.known.non.derived.idai;

import tamil.lang.TamilWord;
import tamil.lang.known.non.derived.AtomicIsolatedIdai;
import tamil.lang.known.non.derived.INonStartingIdaichchol;
import tamil.lang.known.non.derived.IQuestionYesOrNo;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class Ea extends AtomicIsolatedIdai implements INonStartingIdaichchol, IQuestionYesOrNo {
    public static final Ea EA  = new Ea();
    private Ea() {
        super(TamilWord.from("ஏ"));
    }

}
