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
public class Oa extends AtomicIsolatedIdai implements INonStartingIdaichchol, IQuestionYesOrNo {
    public static final Oa OA  = new Oa();
    private Oa() {
        super(TamilWord.from("ஓ"));
    }

}