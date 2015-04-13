package tamil.lang.known.derived;


import my.interest.lang.tamil.EzhuththuUtils;
import tamil.lang.TamilWord;
import my.interest.lang.tamil.generated.types.PaalViguthi;
import tamil.lang.known.non.derived.Vinaiyadi;

/**
 * <p>
 *     A known verb driven word associated person (பால் )   and a verb
 * </p>
 *
 * @author velsubra
 */
public abstract class DerivativeWithPaal extends VinaiyadiDerivative implements HavingPaal {
    PaalViguthi viguthi ;

    public DerivativeWithPaal(TamilWord word, Vinaiyadi vinaiyadi, PaalViguthi viguthi) {
        super(word,vinaiyadi);
        this.viguthi = viguthi;
    }

    public boolean isUyarThinhai() {
        return EzhuththuUtils.isUyarThinhai(getPaalViguthi()) ;
    }


    @Override
    public PaalViguthi getPaalViguthi() {
        return viguthi;
    }
}
