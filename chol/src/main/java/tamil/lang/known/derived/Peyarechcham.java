package tamil.lang.known.derived;

import tamil.lang.TamilWord;
import my.interest.lang.tamil.generated.types.SimpleTense;
import tamil.lang.known.AbstractVinaiyadiPeyarechcham;
import tamil.lang.known.non.derived.Vinaiyadi;

/**
 * <p>
 *    பெயரெச்சம்  எ.கா) படித்த, வந்த [பையன்]
 * </p>
 *
 * @author velsubra
 */
public final class Peyarechcham extends AbstractVinaiyadiPeyarechcham implements HavingTense {
    SimpleTense tense;

    public Peyarechcham(TamilWord word, Vinaiyadi vinaiyadi, SimpleTense tense) {
        super(word, vinaiyadi);
        this.tense = tense;

    }

    @Override
    public SimpleTense getTense() {
        return tense;
    }
}
