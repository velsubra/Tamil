package tamil.lang.known.non.derived;

import tamil.lang.TamilWord;

/**
 * <p>
 *
 * </p>
 *
 * @author velsubra
 */
public class AtomicIsolatedIdai extends AbstractKnownWord implements IIdaichchol {
    public AtomicIsolatedIdai(TamilWord w) {
        super(w);
        this.type = TamilWord.from("-இடைச்சொல்-");
    }

    @Override
    public boolean isAtomic() {
        return true;
    }
}
