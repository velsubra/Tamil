package tamil.lang.known.derived;

import my.interest.lang.tamil.TamilUtils;
import tamil.lang.TamilWord;
import my.interest.lang.tamil.generated.types.PaalViguthi;
import my.interest.lang.tamil.generated.types.SimpleTense;
import tamil.lang.known.non.derived.Vinaiyadi;

/**
 * <p>
 *      A known verb driven word associated person (பால் ) and tense
 * </p>
 *
 * @author velsubra
 */
public abstract class DerivativeWithTenseAndPaal extends VinaiyadiDerivative implements HavingTense, HavingPaal {

    SimpleTense tense;
    PaalViguthi viguthi;

    public DerivativeWithTenseAndPaal(TamilWord word, Vinaiyadi vinaiyadi, SimpleTense tense, PaalViguthi viguthi) {
        super(word, vinaiyadi);
        this.tense = tense;
        this.viguthi = viguthi;


    }
    public boolean isUyarThinhai() {
        return TamilUtils.isUyarThinhai(getPaalViguthi()) ;
    }

    @Override
    public PaalViguthi getPaalViguthi() {
        return viguthi;
    }

    @Override
    public SimpleTense getTense() {
        return tense;
    }

    @Override
    public int compareTo(Object o) {
        int ret = super.compareTo(o);
        if (ret == 0) {
            if ( DerivativeWithTenseAndPaal.class.isAssignableFrom(o.getClass())) {
                return viguthi.compareTo(((DerivativeWithTenseAndPaal) o).getPaalViguthi());
            } else {
                return ret;
            }
        } else {
            return ret;
        }
    }
}
