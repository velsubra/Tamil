package tamil.lang.known.derived;

import tamil.lang.TamilWord;
import tamil.lang.known.non.derived.IPeyarchchol;
import tamil.lang.known.non.derived.Vinaiyadi;

/**
 * <p>
 *     e.g) இணைத்தமை
 * </p>
 *
 * @author velsubra
 */
public final class PeyarechchapPeyar extends  VinaiyadiDerivative implements IPeyarchchol {

    public PeyarechchapPeyar(TamilWord word, Vinaiyadi vinaiyadi) {
        super(word, vinaiyadi);
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
