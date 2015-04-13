package tamil.lang.known.derived;

import my.interest.lang.tamil.generated.types.PaalViguthi;
import tamil.lang.TamilWord;
import tamil.lang.known.non.derived.Vinaiyadi;

/**
 * <p>
 * Nouns based on verb. Unlike {@link tamil.lang.known.derived.VinaiyaalanhaiyumPeyar}, it does not have tense. E.g)
 *
 *  நடப்பன ,
 * வணங்குவன,
 * ஓடுவன,
 *
 *  The பால்விகுதி is always {@link PaalViguthi#A}
 * </p>
 *
 * @author velsubra
 */
public final class Vinaippeyar extends VinaiyadiDerivative implements HavingPaal {

    public Vinaippeyar(TamilWord word, Vinaiyadi vinaiyadi) {
        super(word, vinaiyadi);
    }

    @Override
    public PaalViguthi getPaalViguthi() {
        return PaalViguthi.A;
    }


    @Override
    public boolean isUyarThinhai() {
        return false;
    }
}
