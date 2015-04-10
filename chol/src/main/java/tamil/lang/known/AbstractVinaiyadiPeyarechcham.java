package tamil.lang.known;

import tamil.lang.TamilWord;
import tamil.lang.known.derived.VinaiyadiDerivative;
import tamil.lang.known.non.derived.IPeyarechcham;
import tamil.lang.known.non.derived.Vinaiyadi;

/**
 * <p>
 *      Base for வினையடியைக்கருவாகக்கொண்ட  பெயரெச்சம்.
 * </p>
 *
 * @author velsubra
 */
public abstract  class AbstractVinaiyadiPeyarechcham extends VinaiyadiDerivative implements IPeyarechcham {

    public AbstractVinaiyadiPeyarechcham(TamilWord word, Vinaiyadi vinaiyadi) {
        super(word, vinaiyadi);
    }
}
