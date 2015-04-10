package tamil.lang.known.derived;

import my.interest.lang.tamil.generated.types.PaalViguthi;
import tamil.lang.TamilWord;
import tamil.lang.known.non.derived.IPeyarchchol;
import tamil.lang.known.non.derived.Peyarchchol;

/**
 * <p>
 * குறிப்புப்பெயரெச்சப்பெயர்
 * eg) இனியவள்      ,நல்லவன்
 * </p>
 *
 * @author velsubra
 */
public final class KurrippupPeyarechchappeyar extends PeyarchcholDerivative implements IPeyarchchol, HavingPaal {

    private PaalViguthi viguthi;

    /**
     * Gets the underlying  {@link KurrippupPeyarechcham}
     * @return  எச்சம் never null.
     */
    public KurrippupPeyarechcham getPeyarechcham() {
        return peyarechcham;
    }

    private KurrippupPeyarechcham peyarechcham = null;

    public KurrippupPeyarechchappeyar(TamilWord word, KurrippupPeyarechcham peyar, PaalViguthi viguthi) {
        super(word, peyar.getPeyar());
        this.viguthi = viguthi;
        this.peyarechcham = peyar;
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
