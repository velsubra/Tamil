package tamil.lang.known.thodar;

import tamil.lang.TamilWord;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.ITheriyaachchol;

import java.util.List;

/**
 * <p>
 *     This represents intermediate state of a compound word. For e.g) while it is being identified.
 * </p>
 *
 * @author velsubra
 */
public final class UnknownThodarMozhi extends AbstractThodarMozhi implements ITheriyaachchol {
    public UnknownThodarMozhi(TamilWord word, IKnownWord... knowns) {
        super(word, knowns);
    }

//    @Override
//    public List<TYPE_SIG> getTypes() {
//        return null;
//    }
}
