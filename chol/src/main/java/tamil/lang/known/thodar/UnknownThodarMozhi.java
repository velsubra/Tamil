package tamil.lang.known.thodar;

import tamil.lang.TamilWord;
import tamil.lang.known.IKnownWord;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final class UnknownThodarMozhi extends AbstractThodarMozhi {
    public UnknownThodarMozhi(TamilWord word, IKnownWord... knowns) {
        super(word, knowns);
    }

    @Override
    public List<TYPE_SIG> getTypes() {
        return null;
    }
}
