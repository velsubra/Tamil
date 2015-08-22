package tamil.yaappu.seer;

import tamil.lang.TamilWord;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public abstract class AbstractSeer {
    public TamilWord getValue() {
        return value;
    }

    private TamilWord value;
    public AbstractSeer(TamilWord word) {
        this.value = word;
    }
    public boolean isNtear() {
        return this.getClass() == Ntear.class;
    }

}
