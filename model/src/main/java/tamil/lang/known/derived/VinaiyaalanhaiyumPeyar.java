package tamil.lang.known.derived;

import tamil.lang.TamilWord;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import my.interest.lang.tamil.generated.types.PaalViguthi;
import my.interest.lang.tamil.generated.types.SimpleTense;
import tamil.lang.known.non.derived.IPeyarchchol;
import tamil.lang.known.non.derived.Vinaiyadi;

/**
 * <p>
 *      வினையாலணையும்பெயர் எ.கா) வந்தவன் , வந்தது
 * </p>
 *
 * @author velsubra
 */
public final class VinaiyaalanhaiyumPeyar extends DerivativeWithTenseAndPaal implements IPeyarchchol {
    public VinaiyaalanhaiyumPeyar(TamilWord word, Vinaiyadi vinaiyadi, SimpleTense tense, PaalViguthi viguthi) {
        super(word, vinaiyadi, tense, viguthi);

        if (PaalViguthi.THU == viguthi && SimpleTense.FUTURE == tense) {
            //படுவது   it comes without tense and person.
            ThozhirrPeyar thozhi = new ThozhirrPeyar(word, vinaiyadi);
            PersistenceInterface.addKnown(thozhi);

        }

    }

}
