package tamil.yaappu.asai;

import tamil.lang.TamilWord;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public abstract class AbstractAsai {
    public TamilWord getValue() {
        return value;
    }

    private TamilWord value;
    public AbstractAsai(TamilWord word) {
        this.value = word;
    }
    public boolean isNtear() {
        return this.getClass() == Ntear.class;
    }

}
