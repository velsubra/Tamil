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
public class Aa  extends AtomicIsolatedIdai implements INonStartingIdaichchol, IQuestionYesOrNo {
    public static final Aa AA  = new Aa();
    private Aa() {
        super(TamilWord.from("ஆ"));
    }

}