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
public final class MaiyeettupPeyarechchapPeyar extends  VinaiyadiDerivative implements IPeyarchchol {

    /**
     * Gets the underlying எச்சம்.
     * @return  எச்சம் never null
     */
    public Peyarechcham getPeyarechcham() {
        return peyarechcham;
    }

    private Peyarechcham peyarechcham    = null;

    public MaiyeettupPeyarechchapPeyar(TamilWord word, Peyarechcham echcham) {
        super(word, echcham.getVinaiyadi());
        this.peyarechcham = echcham;
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
