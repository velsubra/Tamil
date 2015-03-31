package tamil.lang.known.derived;

import my.interest.lang.tamil.generated.types.PaalViguthi;
import tamil.lang.TamilWord;
import tamil.lang.known.non.derived.IPeyarchchol;
import tamil.lang.known.non.derived.Peyarchchol;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final class KurrippupPeyarechchappeyar extends PeyarchcholDerivative implements IPeyarchchol, HavingPaal {

    private PaalViguthi viguthi;

    public KurrippupPeyarechchappeyar(TamilWord word, Peyarchchol peyar,PaalViguthi viguthi) {
        super(word, peyar);
        this.viguthi = viguthi;
    }

    @Override
    public PaalViguthi getPaalViguthi() {
        return viguthi;
    }

    @Override
    public boolean isUyarThinhai() {
        return false;
    }

    @Override
    public boolean isProNoun() {
        return false;
    }
}
