package tamil.lang.known.derived;

import tamil.lang.TamilWord;
import my.interest.lang.tamil.generated.types.SimpleTense;
import tamil.lang.known.non.derived.Vinaiyadi;

/**
 * <p>
 *      A known verb driven word associated tense
 * </p>
 *
 * @author velsubra
 */
public abstract  class DerivativeWithTense extends VinaiyadiDerivative implements HavingTense {
    SimpleTense tense;

    public DerivativeWithTense(TamilWord word, Vinaiyadi vinaiyadi, SimpleTense tense) {
        super(word,vinaiyadi);
        this.tense = tense;
    }

    @Override
    public SimpleTense getTense() {
        return tense;
    }
}
